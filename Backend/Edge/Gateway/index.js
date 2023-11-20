import cors from 'cors';
import express, { json, urlencoded } from 'express';
import dotenv from 'dotenv';
dotenv.config({path: 'edge-gateway.env'});

const app = express();

app.use(json());
app.use(urlencoded({extended: false}));
app.use(cors());

const port = process.env.PORT;
app.listen(port, async () => { 
    console.log(`Edge Gateway Service is listening on port ${port}`);
});