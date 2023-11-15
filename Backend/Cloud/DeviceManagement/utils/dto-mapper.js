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

export default { 
    toDevicesDto,
    toDeviceDto
}