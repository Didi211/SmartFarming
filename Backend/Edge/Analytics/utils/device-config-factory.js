import {regular as constants} from "./constants.js";

export const getDeviceConfig = (deviceType) => { 
    if (deviceType === constants.SENSOR) { 
        return { 
            profileName: process.env.SENSOR_PROFILE,
            host: process.env.SENSOR_DEVICE_URL,
            port: process.env.SENSOR_DEVICE_PORT,
            path: process.env.SENSOR_DEVICE_PATH
        }
    }
    else { 
        return { 
            profileName: process.env.ACTUATOR_PROFILE,
            host: process.env.ACTUATOR_DEVICE_URL,
            port: process.env.ACTUATOR_DEVICE_PORT,
            path: process.env.ACTUATOR_DEVICE_PATH
        }
    }
}