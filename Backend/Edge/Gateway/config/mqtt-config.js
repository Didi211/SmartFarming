// import { fetchMqttToken } from '../utils/mqtt-token-getter.js';
import mqtt from 'mqtt'
import { MqttTokenManager } from '../utils/mqtt-token-manager.js';

const mqttCloudClient = (() => { 
    const client = mqtt.connect(process.env.CLOUD_MQTT_URL); 
    client.on('connect', () => { 
        console.log('Connected to Cloud MQTT broker.');
    });
    client.on('error', (error) => { 
        console.log(`Error connecting to Cloud MQTT broker: ${error.message}`);
    });
    return client;
})();

const mqttEdgeClient = (() => { 
    const client = mqtt.connect(process.env.EDGE_MQTT_URL);
    client.on('connect', () => { 
        console.log('Connected to Edge MQTT broker');
    });
    client.on('error', (error) => { 
        console.log(`Error connecting to Edge MQTT broker: ${error}`);
    });
    return client;
})();

const connectToCloud = () => { 
    let token = MqttTokenManager.Token();
    let topic = `${process.env.MQTT_TOPIC_CLOUD_SUB}/${token}/#`;
    if (mqttCloudClient.connected) { 
        mqttCloudClient.subscribe(topic);
        console.log(`Subcribed to topic on cloud: (${topic}).`);
    }
    else {
        console.log('MQTT Connection to Cloud not established.');
    }
}


const connectToEdge = () => { 
    let rtDataTopic = `${process.env.MQTT_TOPIC_RT_DATA_EDGE}`;
    if (mqttEdgeClient.connected) { 
        mqttEdgeClient.subscribe(rtDataTopic);
        console.log(`Subcribed to MQTT topic on edge: (${rtDataTopic}).`);
    }
    else {
        console.log('MQTT Connection to Edge not established.');
    }
}

const config = async () => { 

    try { 
        connectToCloud();
        connectToEdge();
    }
    catch(error) { 
        console.log(error);
    }
}

export { 
    config,
    mqttEdgeClient,
    mqttCloudClient
}
