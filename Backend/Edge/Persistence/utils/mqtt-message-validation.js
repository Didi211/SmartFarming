import { propertyNames } from "./constants.js";

export const runMqttMessageChecking = (message) => { 
    if (!isJsonParsable(message)) { 
        throw "Message from MQTT is not in JSON format."
    }
    let messageJson = JSON.parse(message);
    throwIfUndefined(messageJson, propertyNames.PROFILE_NAME);
    throwIfUndefined(messageJson, propertyNames.DEVIEC_NAME);
    throwIfUndefined(messageJson, propertyNames.READINGS);
    throwIfUndefined(messageJson.readings[0], propertyNames.VALUE);
    throwIfUndefined(messageJson, propertyNames.TAGS);
    throwIfUndefined(messageJson.tags, propertyNames.MONGO_ID);

    if (messageJson.profileName !== process.env.SENSOR_PROFILE) {
        throw `Message is not from profile ${process.env.SENSOR_PROFILE}`;
    }
    
}

const throwIfUndefined = (obj, propertyName) => { 
    if (obj[propertyName] == undefined) { 
        throw `Message from MQTT does not have '${propertyName}' property.`
    }
}

const isJsonParsable = (message) => { 
    try { 
        JSON.parse(message);
        return true;
    }
    catch(error) { 
        return false;
    }
} 