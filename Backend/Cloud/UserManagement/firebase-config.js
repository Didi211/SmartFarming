import { initializeApp, } from "firebase/app";
import dotenv from 'dotenv';

dotenv.config({path: 'user-management.env'});

const firebaseConfig = {
    apiKey: process.env.FIREBASE_API_KEY,
    authDomain: process.env.AUTH_DOMAIN,
    projectId: process.env.PROJECT_ID,
    storageBucket: process.env.STORAGE_BUCKET,
    messagingSenderId: process.env.MESSAGING_SENDER_ID,
    appId: process.env.APP_ID
};

export const firebaseInstance = initializeApp(firebaseConfig);

