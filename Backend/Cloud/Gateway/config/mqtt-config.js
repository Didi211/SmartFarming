import mqtt from 'mqtt';

const mqttClient = mqtt.connect(process.env.MQTT_URL);

let alertTopic = process.env.MQTT_TOPIC_ALERTS;

mqttClient.on('connect', () => {
    console.log('Connected to MQTT broker');
    mqttClient.subscribe(alertTopic);
    console.log(`Subcribed to topic: (${alertTopic}).`);
});

export { mqttClient }