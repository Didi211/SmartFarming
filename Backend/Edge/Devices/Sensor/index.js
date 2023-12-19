import cors from 'cors';
import express, { json, urlencoded } from 'express';
import deviceRoutes from './routes/device-routes.js';
import dotenv from 'dotenv';
dotenv.config({path: 'sensor.env'});
import { startDataSimulation } from './scheduler/data-simulator.js';
import edgexServiceHealthChecker from './edgex-service-health-checker.js';

const app = express();

app.use(json());
app.use(urlencoded({extended: false}));
app.use(cors());


app.use('/api/device', deviceRoutes);

const port = process.env.PORT;
app.listen(port, async () => { 
    await edgexServiceHealthChecker.waitForRestDeviceService();
    startDataSimulation();
    console.log(`Sensor Simulator Service is listening on port ${port}`);
})


