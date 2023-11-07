import cors from 'cors';
import express, { json, urlencoded } from 'express';
import dotenv from 'dotenv';
// import { edgeGateway, persistence } from './axios-config';
import deviceRoutes from './routes/device-routes.js';


const app = express();

dotenv.config();

app.use(json());
app.use(urlencoded({extended: false}));
app.use(cors());

app.use('/api/devices',deviceRoutes);


const port = process.env.PORT;
app.listen(port, () => { 
    console.log(`Server is listening on port ${port}`);
})