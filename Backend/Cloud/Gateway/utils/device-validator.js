

const validate = (device) => { 
    let result = "";
    try { 
        if (device.name == undefined || device.name == "") { 
            throw "Device name is required."
        }
        if (device.type == undefined || device.type == "") { 
            throw "Device type is required."
        }
        let typeUpperCase = device.type.toUpperCase();
        if (typeUpperCase != 'SENSOR' && typeUpperCase != 'ACTUATOR') { 
            throw "Device type must be either SENSOR or ACTUATOR."
        }
        if (device.userId == undefined || device.userId == "") { 
            throw "User Id for device is required field.";
        }
    }
    catch(error) { 
        result = error;
    }
    return result;
}

const validateForUpdate = (id, device) => { 
    let result = "";
    try { 
        if (device.id == undefined || device.id == "") { 
            throw "Device ID cannot be removed when updating device."
        }
        if (id != device.id) { 
            throw "Device ID cannot be updated."
        }
        result = validate(device)
        if (result != "") { 
            throw result
        }
    }
    catch(error) { 
        result = error;
    }
    return result;
}

export default { 
    validate,
    validateForUpdate
}