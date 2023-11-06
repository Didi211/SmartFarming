import cors from 'cors';
import express, { json, urlencoded } from 'express';
import dotenv from 'dotenv';
// import { edgeGateway, persistence } from './axios-config';

const app = express();
    
dotenv.config();

app.use(json());
app.use(urlencoded({extended: false}));

const port = process.env.PORT;
app.listen(port, () => { 
    console.log(`Server is listening on port ${port}`);
})