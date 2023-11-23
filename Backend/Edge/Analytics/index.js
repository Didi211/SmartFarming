import cors from 'cors';
import express, { json, urlencoded } from 'express';
import dotenv from 'dotenv';

dotenv.config({path: 'edge-analytics.env'});

import * as axios from './config/axios-config.js';
import deviceRoutes from './routes/device-routes.js';
import ruleRoutes from './routes/rule-routes.js'
const app = express();
app.use(json());
app.use(urlencoded({extended: false}));
app.use(cors());
app.use('/api/devices', deviceRoutes);
app.use('/api/rules', ruleRoutes);
const port = process.env.PORT;
app.listen(port, async () => { 
    console.log(`Edge Analytics Service is listening on port ${port}`);
});