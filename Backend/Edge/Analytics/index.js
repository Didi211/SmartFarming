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
import { waitForServiceToBeReady } from './utils/service-checker.js';
import { createRuleStream } from './config/edgex-rule-stream-loader.js';

const port = process.env.PORT;
app.listen(port, async () => { 
    await Promise.all([
        mongoClient.config(),
        waitForServiceToBeReady('Edgex Core Metadata', process.env.EDGEX_CORE_METADATA_URL),
        waitForServiceToBeReady('Edgex Rule Engine', process.env.EDGEX_RULES_ENGINE_URL)
    ]);
    await Promise.all([
        loadEdgexProfiles(),
        createRuleStream()
    ])

    startListeningRTData();
    console.log(`Edge Analytics Service is listening on port ${port}`);
});