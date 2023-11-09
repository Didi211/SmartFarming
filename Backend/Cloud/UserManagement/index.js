// Import the functions you need from the SDKs you need
import cors from 'cors';
import express, { json, urlencoded } from 'express';
import routes from './routes/route.js';
import { firebaseInstance } from './firebase-config.js';

const app = express();


app.use(json());
app.use(urlencoded({extended: false}));
app.use(cors());

app.use('/api/users', routes);

const port = process.env.PORT;
app.listen(port, () => { 
    console.log(`Server is listening on port ${port}`);
})