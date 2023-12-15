import cors from 'cors';
import express, { json, urlencoded } from 'express';
import dotenv from 'dotenv';

dotenv.config({path: 'edge-analytics.env'});

import mongoClient from './config/mongodb-config.js';


import { edgexMetadataAxios } from './config/axios-config.js';

import deviceRoutes from './routes/device-routes.js';
import ruleRoutes from './routes/rule-routes.js'

const app = express();

app.use(json());
app.use(urlencoded({extended: false}));
app.use(cors());

app.use('/api/updates/devices', deviceRoutes);
app.use('/api/updates/rules', ruleRoutes);

import { startListening as startListeningRTData } from './messaging/rt-data-mqtt.js';
import { loadEdgexProfiles } from './config/edgex-profile-loader.js';
import edgexLogic from './logic/edgex-logic.js';

const port = process.env.PORT;
app.listen(port, async () => { 
    await mongoClient.config();
    await edgexLogic.waitForMetadataToBeReady();
    await loadEdgexProfiles();
    startListeningRTData();
    console.log(`Edge Analytics Service is listening on port ${port}`);
});