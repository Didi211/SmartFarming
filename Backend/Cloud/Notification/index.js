import cors from 'cors';
import express, { json, urlencoded } from 'express';
import dotenv from 'dotenv';
import routes from './routes/routes.js'

dotenv.config({path: 'notification.env'});
import mongoClient from './mongodb-config.js';



const app = express();


app.use(json());
app.use(urlencoded({ extended: false }));
app.use(cors());

app.use('/api/notifications', routes);

try { 
    await mongoClient.config();
    const port = process.env.PORT;
    app.listen(port, () => { 
        console.log(`Notification Service is running on port ${port}`);
    });
}
catch(error) { 
    console.log(error);
}