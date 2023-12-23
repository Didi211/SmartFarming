import mqtt from 'mqtt';

const mqttClient = mqtt.connect(process.env.EDGE_MQTT_URL);

let kuiperUpdateTopic = `${process.env.KUIPER_TOPIC}`;

mqttClient.on('connect', () => {
    console.log('Connected to MQTT broker');
    mqttClient.subscribe(kuiperUpdateTopic);
    console.log(`Subcribed to topic: (${kuiperUpdateTopic}).`);
});

export { mqttClient }