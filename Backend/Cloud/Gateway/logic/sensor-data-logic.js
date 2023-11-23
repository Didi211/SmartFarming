import { sensorDataAxios } from '../config/axios-config.js';
import deviceLogic from './device-logic.js';

const saveSensorData = async (userId, data) => { 
    await validateUserSensors(data, userId);

    let response = await sensorDataAxios.post(`/sync`, JSON.stringify({ 
        data: data
    }), { 
        headers: { 
            'user-id': userId 
        }
    });
    if (response.status == 200) { 
        return JSON.parse(response.data);
    }
    else { 
        throw response.data;
    }
}

const getHistoryData = async (data) => { 
    let response = await sensorDataAxios.post(`/${data.sensorId}?period=${data.period}`, {
        startDate: data.startDate,
        endDate: data.endDate
    }, { 
        headers: { 
            'user-id': data.userId
        } 
    });

    if (response.status == 200) { 
        return JSON.parse(response.data);
    }
    else { 
        throw response.data;
    }
}


const validateUserSensors = async (userSensors, userId) => { 
    let userSensorsResult = await deviceLogic.getAllDevices(userId, 'SENSOR');
    if (userSensorsResult.status == 200) { 
        let sensors = userSensorsResult.details;
        let ids = sensors.map(x => x.id);
        userSensors.forEach(sensor => { 
            let found = ids.find(id => id == sensor.sensorId);
            if (!found) { 
                throw { 
                    status: 400,
                    message: "Unregistered sensor ID",
                    details: `Sensor with ID [${sensor.sensorId}] does not belong to user [${userId}]`
                }
            }
        });
    }
}


export default { 
    saveSensorData,
    getHistoryData
}