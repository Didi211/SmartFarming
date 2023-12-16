import { edgeGatewayAxios, edgexCoreMetadataAxios } from "../config/axios-config.js"

const sendStatusUpdate = async (data) => { 
    let response = await edgeGatewayAxios.post('/alert', JSON.stringify(data));
    if (response.status == 200) { 
        return JSON.parse(response.data);
    }
    else { 
        throw response.data;
    }
}

// move this to device management service to simulator/scheduler
const updateDeviceStatusOnEdgex = async (id, device) => { 
    let operatingState = device.status == constants.ONLINE ? constants.STATE_UP : constants.STATE_DOWN;
    let data = [
        {
          "apiVersion": "v3",
          "device": {
            "id": id,
            "operatingState": operatingState,
          }
        }
    ]
    try {
        let response = await edgexCoreMetadataAxios.patch('/device',JSON.stringify(data));
        if (response.status != 207) { 
            throw error
        }
    } catch (error) {
        throw error;
    }
}

const fetchAllDevices = async (profileName) => { 
    try {
        let response = await edgexCoreMetadataAxios.get(`/device/profile/name/${profileName}`);
        if (response.status != 200) { 
            throw response.data
        }
        let data = JSON.parse(response.data);
        return data.devices;
    } catch (error) {
        throw error;
    }
}

export default { 
    sendStatusUpdate,
    updateDeviceStatusOnEdgex,
    fetchAllDevices,
}