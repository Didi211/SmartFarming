
import { publishAlert } from "../messaging/alert-mqtt.js";

const sendAlertToCloud = async (data) => { 
    try {
        publishAlert(data);
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