import { edgeGatewayAxios, edgexCoreMetadataAxios } from "../config/axios-config.js"
import constants from "../utils/constants.js";

const sendStatusUpdate = async (data) => { 
    let response = await edgeGatewayAxios.post('/alert', JSON.stringify(data));
    if (response.status == 200) { 
        return JSON.parse(response.data);
    }
    else { 
        throw response.data;
    }
}

const updateDeviceStatusOnEdgex = async (id, status) => { 
    let toggledStatus = status === constants.STATE_UP
        ? constants.STATE_DOWN 
        : constants.STATE_UP 
    let data = [
        {
          "apiVersion": "v3",
          "device": {
            "id": id,
            "operatingState": toggledStatus,
          }
        }
    ]
    try {
        let response = await edgexCoreMetadataAxios.patch('/device',JSON.stringify(data));
        if (response.status != 207) { 
            throw response.data
        }
        let responseData = JSON.parse(response.data);
        if (responseData[0].statusCode != 200) { 
            throw response.data;
        }
        return toggledStatus;
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