import mqtt from 'mqtt';

const mqttClient = mqtt.connect(process.env.EDGE_MQTT_URL);

let rtDataTopic = `${process.env.MQTT_TOPIC_RT_DATA}`;

mqttClient.on('connect', () => {
    console.log('Connected to MQTT broker');
    mqttClient.subscribe(rtDataTopic);
    console.log(`Subcribed to topic: (${rtDataTopic}).`);
});

export { mqttClient }