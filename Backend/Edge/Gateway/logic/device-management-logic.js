
import { analyticsAxios, cloudGatewayAxios } from "../config/axios-config.js";
import { publishAlert } from "../messaging/alert-mqtt.js";

const sendAlertToCloud = async (data) => { 
    try {
        publishAlert(data);
        let deviceId = data.metadata.deviceId;
        let response = await analyticsAxios.put(`/devices/${deviceId}/status`, JSON.stringify({
            status: data.metadata.status
        }));
    } catch (error) {
        throw { 
            status: 500,
            message: "Publishing alert error",
            details: error
        }
    }
}

const sendStateUpdateToCloud = async (data) => {
    try {
        let deviceId = data.deviceId;
        let response = await cloudGatewayAxios.put(`/devices/actuator/${deviceId}/state`, JSON.stringify({
            state: data.state
        }));
        if (response.status != 200) { 
            throw response.data;
        }
    } catch (error) {
        throw { 
            status: 500,
            message: "Publishing alert error",
            details: error
        }
    }
}

export default { 
    sendAlertToCloud,
    sendStateUpdateToCloud
}