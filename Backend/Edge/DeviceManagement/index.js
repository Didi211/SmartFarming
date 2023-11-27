import cors from 'cors';
import express, { json, urlencoded } from 'express';
import dotenv from 'dotenv';
import * as axios from './config/axios-config.js';

dotenv.config({path: 'edge-device-management.env'});

const app = express();

app.use(json());
app.use(urlencoded({extended: false}));
app.use(cors());

import { startStatusScheduler } from './scheduler/status-scheduler.js';


const port = process.env.PORT;
app.listen(port, async () => { 
    // startStatusScheduler();
    console.log(`Edge Device Management Service is listening on port ${port}`);
});