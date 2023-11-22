import cors from 'cors';
import express, { json, urlencoded } from 'express';
import dotenv from 'dotenv';

dotenv.config({path: 'edge-persistence.env'});

import { influxDbConfig } from './config/influxdb-config.js';
import * as axios from './config/axios-config.js';


const app = express();

app.use(json());
app.use(urlencoded({ extended: false }));
app.use(cors());

import { startListening as startListeningRTData} from './messaging/rt-data-mqtt.js';

try { 
    influxDbConfig();
    const port = process.env.PORT;
    app.listen(port, () => { 
        startListeningRTData();
        console.log(`Edge Persistence Service is running on port ${port}`);
    });
}
catch(error) { 
    console.log(error);
}