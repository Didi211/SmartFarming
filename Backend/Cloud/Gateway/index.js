import cors from 'cors';
import express, { json, urlencoded } from 'express';
import * as axios from './axios-config.js';
import deviceRoutes from './routes/device-routes.js';
import userRoutes from './routes/user-routes.js';
import notificationRoutes from './routes/notification-routes.js';
import sensorDataRoutes from './routes/sensor-data-routes.js';


const app = express();



app.use(json());
app.use(urlencoded({extended: false}));
app.use(cors());

app.use('/api/devices',deviceRoutes);
app.use('/api/users', userRoutes);
app.use('/api/notifications', notificationRoutes);
app.use('/api/sensor-data', sensorDataRoutes);




const port = process.env.PORT;
app.listen(port, () => { 
    console.log(`Server is listening on port ${port}`);
})