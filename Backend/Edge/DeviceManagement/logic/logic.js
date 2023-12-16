import { edgeGatewayAxios, edgexCoreMetadataAxios } from "../config/axios-config.js"

const sendAlert = async (data) => { 
    let response = await edgeGatewayAxios.post('/alert', JSON.stringify(data));
    if (response.status == 200) { 
        return JSON.parse(response.data);
    }
    else { 
        throw response.data;
    }
}

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

export default { 
}