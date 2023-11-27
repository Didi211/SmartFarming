
import { analyticsAxios } from "../config/axios-config.js";
import { publishAlert } from "../messaging/alert-mqtt.js";

const sendAlertToCloud = async (data) => { 
    try {
        publishAlert(data);
        console.log('alert', data);
        let sensorId = data.metadata.sensorId;
        let response = await analyticsAxios.put(`/devices/${sensorId}/status`, JSON.stringify({
            status: 'OFFLINE'
        }));
        console.log(response.data);

    } catch (error) {
        throw { 
            status: 500,
            message: "Publishing alert error",
            details: error
        }
    }
}

export default { 
    sendAlertToCloud
}