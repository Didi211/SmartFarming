const toDeviceDto = (device) => { 
    return { 
        id: device._id,
        name: device.name,
        userId: device.userId,
        type: device.type,
        status: device.status,
        unit: device.unit ?? null,
        state: device.state ?? null

    }
}
const toDevicesDto = (devices) => { 
    return devices.map(device => toDeviceDto(device));
}

const toRuleDto = (rule) => { 
    return { 
        id: rule._id,
        name: rule.name,
        sensorId: rule.sensorId, 
        actuatorId: rule.actuatorId,
        startExpression: rule.startExpression,
        stopExpression: rule.stopExpression,
        startTriggerLevel: rule.startTriggerLevel,
        stopTriggerLevel: rule.stopTriggerLevel,
        text: rule.text
    };
}

export default { 
    toDevicesDto,
    toDeviceDto,
    toRuleDto
}