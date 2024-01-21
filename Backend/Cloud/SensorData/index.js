import cors from 'cors';
import express, { json, urlencoded } from 'express';
import routes from './routes/routes.js'
import dotenv from 'dotenv';

dotenv.config({path: 'sensor-data.env'});
import * as axios from  './config/axios-config.js'
import { influxDbConfig } from './config/influxdb-config.js';

const app = express();

app.use(json());
app.use(urlencoded({ extended: false }));
app.use(cors());

app.use('/api/sensor-data', routes);

try { 
    influxDbConfig();
    const port = process.env.PORT;
    app.listen(port, () => { 
        console.log(`Sensor Data Service is running on port ${port}`);
    });
}
catch(error) { 
    console.log(error);
}