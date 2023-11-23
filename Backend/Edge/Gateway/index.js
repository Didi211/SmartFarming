import cors from 'cors';
import express, { json, urlencoded } from 'express';
import dotenv from 'dotenv';
dotenv.config({path: 'edge-gateway.env'});
import * as axios from './config/axios-config.js';
import deviceManagementRoutes from './routes/device-management-routes.js';
import persistenceRoutes from './routes/persistence-routes.js';

const app = express();

app.use(json());
app.use(urlencoded({extended: false}));
app.use(cors());

app.use('/api/device-management', deviceManagementRoutes);
app.use('/api/persistence', persistenceRoutes);

import { config as mqttConfig } from './config/mqtt-config.js';
import { startListening as startListeningRTData } from './messaging/rt-data-mqtt.js';
import { MqttTokenManager } from './utils/mqtt-token-manager.js';
import { startListening as startListeningDeviceUpdates } from './messaging/device-mqtt.js';

await MqttTokenManager.initialize(process.env.USER_EMAIL);

const port = process.env.PORT;
app.listen(port, async () => { 
    // configure mqtt 
    await mqttConfig();
    startListeningRTData();
    // startListeningDeviceUpdates();
    console.log(`Edge Gateway Service is listening on port ${port}`);
});