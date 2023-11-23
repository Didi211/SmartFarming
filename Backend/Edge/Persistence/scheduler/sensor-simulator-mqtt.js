import { mqttClient } from "../config/mqtt-config.js";
import cron from 'node-cron';

export const startSimulating = () => { 
    cron.schedule('* * * * *', () => {
        let ids = [
            '655613dab90f7a70bb421a26',
            '655b4e10a3888cb4decb1386',
            '65590b32089bde82a8c8844d',
            '655b34a377743f79f20ea2ed',
            '655b4d2ca3888cb4decb137e',
            '655b4d8aa3888cb4decb1380',
            '655b4da5a3888cb4decb1382',
        ]
        ids.forEach(id => { 
            let topic = `${process.env.MQTT_TOPIC_RT_DATA}/${id}`
            let reading = Math.random() % 100 * 100; 
            mqttClient.publish(topic, JSON.stringify({
                reading: reading
            }));
            console.log(`Published: ${reading} `);
        })
    });
}