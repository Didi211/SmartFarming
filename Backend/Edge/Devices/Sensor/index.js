import cron from 'node-cron';
import cors from 'cors';
import express, { json, urlencoded } from 'express';
import deviceRoutes from './routes/device-routes.js';
// import dataRoutes from './routes/data-routes.js';
import dotenv from 'dotenv';
dotenv.config({path: 'sensor.env'});
// import { edgexDeviceRestAxios } from './config/axios-config.js';
import { SensorModel } from './models/sensor-model.js';

const app = express();

app.use(json());
app.use(urlencoded({extended: false}));
app.use(cors());


app.use('/api/device', deviceRoutes);
app.use('/api/data', dataRoutes);

const port = process.env.PORT;
app.listen(port, () => { 
    console.log(`Sensor Simulator Service is listening on port ${port}`);
})

// generating data every minute
cron.schedule('*/30 * * * * *', async () => { 
    console.log('Simulating sensor readings:');
    
    let sensors = SensorModel.find();
    console.log(sensors);
    for (let sensor of sensors) {
        // Simulate a change between -10 and 10
        let changeAmount = Math.random() * 10;
        
        // Apply the change based on whether it's a raise or not
        let newHumidity = sensor.pumpState
            ? Math.min(sensor.lastReading + changeAmount, 100) // Ensure it doesn't go above 100
            : Math.max(sensor.lastReading - changeAmount, 0); // Ensure it doesn't go below 0
        sensor.lastReading = newHumidity;
        console.log(`Sensor: [${sensor._id}] - Humidity: [${sensor.lastReading}]`);
        sensor.save();

        // send data to edgex 
        // try { 
        //     let response = await edgexDeviceRestAxios.post(`/device/name/${sensor._id}/Humidity`, JSON.stringify({
        //         Humidity: sensor.lastReadingc
        //     }));
        //     console.log(response.data);
        // }
        // catch(err) { 
        //     console.log(err);
        // }
    }

})
