import { mqttClient } from "../config/mqtt-config.js";
import cron from 'node-cron';

export const startSimulating = () => { 
    cron.schedule('* * * * *', () => {
        let topic = `${process.env.MQTT_TOPIC_RT_DATA}/655b4d2ca3888cb4decb137e`
        let reading = Math.random() % 100 * 100; 
        mqttClient.publish(topic, JSON.stringify({
            reading: reading
        }));
        console.log(`Published: ${reading} `);
    });
}