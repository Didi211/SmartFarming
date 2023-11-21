
import { publishAlert } from "../messaging/alert-mqtt.js";

const sendAlertToCloud = async (message) => { 
    try {
        publishAlert(message);
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