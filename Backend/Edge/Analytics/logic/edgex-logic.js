import { edgexMetadataAxios } from "../config/axios-config.js"
import { regular as constants } from "../utils/constants.js"
import { getDeviceConfig } from "../utils/device-config-factory.js";

const addDevice = async (device) => { 
    let operatingState = device.status === constants.ONLINE ? constants.STATE_UP : constants.STATE_DOWN;
    let deviceConfig = getDeviceConfig(device.type);
    let data = [
        {
          "apiVersion": "v3",
          "device": {
            "name": device.name,
            "description": device.description ?? null,
            "adminState": "UNLOCKED",
            "operatingState": operatingState,
            "labels": [
                device.type
            ],
            "serviceName": "device-rest",
            "profileName": deviceConfig.profileName,
            "protocols": {
              "REST": { 
                "Host": deviceConfig.host,
                "Port": deviceConfig.port,
                "Path": deviceConfig.path,
              }
            },
            "tags": { 
              "mongoId": device.id
            }
          }
        }
    ]
    try {
        let response = await edgexMetadataAxios.post('/device',JSON.stringify(data));
        if (response.status != 207) { 
            throw error
        }
        return (JSON.parse(response.data))[0].id; // device ID in edgex 
    } catch (error) {
        throw error;
    }
    
}

const updateDevice = async (id, device) => { 
    let data = [
        {
          "apiVersion": "v3",
          "device": {
            "id": id,
            "name": device.name,
            "description": device.description ?? null,
          }
        }
    ]
    try {
        let response = await edgexMetadataAxios.patch('/device',JSON.stringify(data));
        if (response.status != 207) { 
            throw error
        }
    } catch (error) {
        throw error;
    }
}



const removeDevice = async (name) => { 
    try {
        let response = await edgexMetadataAxios.delete(`/device/name/${name}`);
        if (response.status != 200) { 
            throw error
        }
    } catch (error) {
        throw error;
    }
}

const waitForMetadataToBeReady = async () => {
    let edgexCoreMetadataReady = false; 
    while (!edgexCoreMetadataReady) {
        try {
          let ready = await edgexMetadataAxios.get('/ping');
          if (ready.status === 200) {
            edgexCoreMetadataReady = true; // Service is ready
          } else {
            await new Promise(resolve => setTimeout(resolve, 1000)); // Wait for one second before the next attempt
          }
        } catch (error) {
          // Handle errors, e.g., log or throw an exception
          console.error("Error while checking EdgeX Core Metada service status:", error);
          await new Promise(resolve => setTimeout(resolve, 1000)); // Wait for one second before the next attempt
        }
    }
}

export default { 
    addDevice,
    updateDevice,
    removeDevice,
    waitForMetadataToBeReady
}