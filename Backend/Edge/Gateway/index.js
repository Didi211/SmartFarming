import cors from 'cors';
import express, { json, urlencoded } from 'express';
import dotenv from 'dotenv';
dotenv.config({path: 'edge-gateway.env'});

import deviceManagementRoutes from './routes/device-management-routes.js';
import persistenceRoutes from './routes/persistence-routes.js';

const app = express();

app.use(json());
app.use(urlencoded({extended: false}));
app.use(cors());

app.use('/api/device-management', deviceManagementRoutes);
app.use('/api/persitence', persistenceRoutes);

const port = process.env.PORT;
app.listen(port, async () => { 
    console.log(`Edge Gateway Service is listening on port ${port}`);
});