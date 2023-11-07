const getAllDevices = (type) => { 
    let devices = [];
    for (let i = 0; i < 5; i++) { 
        if (i % 2 == 0) { 
            devices.push({
                id: i,
                name: `Device-${i}`,
                description: "Device description",
                type: 'SENSOR',
                status: 'Online',
                readings: Math.random() % 100 * 100,
            })
        }
        else { 
            devices.push({
                id: i,
                name: `Device-${i}`,
                description: "Device description",
                type: 'ACTUATOR',
                status: 'Offline',
                state: 'On'
            })
        }
    }
    if (type != undefined) { 
        return devices.filter(device => device.type == type);
    }
    return devices;
}

const getDevice = (id) => { 
    return {
        id: id,
        name: `Device-${id}`,
        description: "Device description",
        type: 'Actuator',
        status: 'Offline',
        state: 'On'
    }
}

const addDevice = (device) => { 
    let id = Math.random() % 10 * 10;
    return {
        id: id,
        name: device.name,
        description: device.description,
        type: device.type.toUpperCase(),
        status: 'Offline',
        state: 'Off'
    }
}

const updateDevice = (device) => { 
    return device
}

const removeDevice = (id) => { 
    if (id > 10) { 
        throw { 
            code: 400,
            message: "Object not found.",
            details: `Device with ID=[${id}] not found in databse.`
        }
    }
    return true
}


const getRuleFromDeviceId = (id) => { 
    let ruleId = Math.ceil(Math.random() % 10);
    return { 
        id: ruleId,
        name: `Rule - ${ruleId}`,
        sensorId: '1',
        actuatorId: '2',
        triggerLevel: 40,
        expression: '>', // <, > 
        action: 'START', // START, STOP
        text: `Start pump when humidity level is > 40.`
    }
}

const addRule = async (rule) => { 
    return { 
        id: 1,
        name: rule.name != undefined ? rule.name : 'Rule',
        sensorId: rule.sensorId,
        actuatorId: rule.actuatorId,
        triggerLevel: rule.triggerLevel,
        expression: rule.expression, // <, > 
        action: rule.action, // START, STOP
    }
}

const updateRule = async (rule) => { 
    return rule
}

const removeRule = async (id) => { 
    if (id > 10) { 
        throw { 
            code: 400,
            message: "Object not found.",
            details: `Rule with ID=[${id}] not found in databse.`
        }
    }
    return true
}

export default { 
    getAllDevices,
    getDevice,
    addDevice,
    updateDevice,
    removeDevice,

    getRuleFromDeviceId,
    addRule,
    updateRule,
    removeRule
}