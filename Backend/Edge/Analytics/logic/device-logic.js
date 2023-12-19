import { Device } from '../models/device-model.js';
import { Rule } from '../models/rule-model.js';
import { regular as constants }  from '../utils/constants.js';
import dtoMapper from '../utils/dto-mapper.js';
import edgexLogic from './edgex-logic.js';
import { deviceSimulatorAxios } from '../config/axios-config.js';

const addDevice = async (device) => { 
    try { 
        // create device in mongo
        let deviceModel = new Device(device);
        deviceModel._id = device.id; // set id from cloud 
        let deviceDb = (await Device.create(deviceModel));

        // create device in edgex
        let deviceDto = dtoMapper.toDeviceDto(deviceDb);
        let edgexId = await edgexLogic.addDevice(deviceDto);
        
        // set edgex id in mongo object
        deviceDb = await Device.findByIdAndUpdate(deviceDb.id, { 
            edgexId: edgexId
        }, { new: true});

        
        deviceDto = dtoMapper.toDeviceDto(deviceDb);

        if (process.env.DEVICE_SIMULATOR_ENABLED) { 
            if (deviceDto.type === constants.SENSOR) { 
                await deviceSimulatorAxios.post('/add/sensor', JSON.stringify({
                    id: deviceDto.id,
                    sensorName: deviceDto.name
                }));
            }
            // if ACTUATOR call api only when rule is created
        }
        return deviceDto;
    }
    catch(error) { 
        throw { 
            status: 500,
            message: 'Error while adding new device',
            details: error
        };
    }
}

const updateDevice = async (id, device) => { 
    // validation
    if (id != device.id) { 
        throw { 
            status: 400,
            message: 'Forbidden update.',
            details: `Device Id cannot be updated.`
        }
    }
    let deviceModel = new Device(device);
    await deviceModel.validate();

    let deviceDb = await Device.findById(id);
    if (deviceDb.name !== device.name) { 
        throw { 
            status: 400,
            message: 'Forbidden update.',
            details: `Device name cannot be updated.`
        }
    }
    await edgexLogic.updateDevice(deviceDb.edgexId, device);

    let result = await Device.findByIdAndUpdate(id, {
        name: device.name,
        // status: device.status, // updating directly from edge
        unit: deviceDb.type == 'SENSOR' ? device.unit : null,
        // state: deviceDb.type == 'ACTUATOR' ? device.state : null // kuiper updates this field
    });
    if (!result) { 
        throw { 
            status: 400,
            message: 'MongoDB error',
            details: `Device with ID [${id}] not found in database.`
        }
    }

    if (process.env.DEVICE_SIMULATOR_ENABLED) { 
        if (deviceDb.type === constants.SENSOR) { 
            await deviceSimulatorAxios.post(`/update/sensor/${device.id}`, JSON.stringify({
                sensorName: device.name
            }));
        }
        else { 
            await deviceSimulatorAxios.post(`/update/actuator-name`, JSON.stringify({ 
                oldActuatorName: deviceDb.name,
                newActuatorName: device.name
            }));
        }
    }
}

const removeDevice = async (id) => { 
    let hasRule = await Rule.findOne({
        $or: [
            {sensorId: id},
            {actuatorId: id}
        ]
    });
    if (hasRule) { 
        throw { 
            status: 400,
            message: "MongoDB error",
            details: `Device with ID [${id}] has a rule. Delete rule first then device.`
        };
    }
    try { 
        // remove in edgex also 
        
        let device = await Device.findByIdAndDelete(id);
        console.log('device', device);
        await edgexLogic.removeDevice(device.name);

        if (process.env.DEVICE_SIMULATOR_ENABLED) { 
            let path = `/remove/${device.type.toLowerCase()}/${device.name}`;
            console.log('path', path);
            let simulatorResponse = await deviceSimulatorAxios.delete(path);
            console.log(simulatorResponse);
        }
    }
    catch(error) { 
        throw { 
            status: 500,
            message: 'Deletion error',
            details: error
        }
    }
}

const changeState = async (id, state) => { 
    if (state.toUpperCase() != constants.ON && state.toUpperCase() != constants.OFF) { 
        throw { 
            status: 400,
            message: 'Invalid STATE value',
            details: `State [${state}] is not valid, only [${constants.ON},${constants.OFF}] are valid.`
        }
    }
    let actuator = await Device.findOne({_id: id, type: constants.ACTUATOR});
    if (!actuator) { 
        throw { 
            status: 400,
            message: 'MongoDB error',
            details: `Device with ID [${id}] not found in database. It is not created or it is sensor.`
        }
    }
    await Device.findByIdAndUpdate(id, {
        state: state,
    });
}

const changeStatus = async (id, status) => { 
    if (status.toUpperCase() != constants.ONLINE && status.toUpperCase() != constants.OFFLINE) { 
        throw { 
            status: 400,
            message: 'Invalid STATUS value',
            details: `Status [${status}] is not valid, only [${constants.ONLINE},${constants.OFFLINE}] are valid.`
        }
    }

    let result = await Device.findByIdAndUpdate(id, {
        status: status,
    });
    if (!result) { 
        throw { 
            status: 400,
            message: 'MongoDB error',
            details: `Device with ID [${id}] not found in database.`
        }
    }
}

export default { 
    addDevice,
    updateDevice,
    removeDevice,
    changeState,
    changeStatus
}