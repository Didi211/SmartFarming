import cors from 'cors';
import express, { json, urlencoded } from 'express';
import dotenv from 'dotenv';
import routes from './routes/routes.js'

dotenv.config({path: 'sensor-data.env'});
import { influxDbConfig } from './influxdb-config.js';

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