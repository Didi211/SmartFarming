import { json } from 'express';
import {usersAxios } from '../axios-config.js';

const login = async (email, password) => { return true }

const register = async (user) => { return true }

const fetchMqttToken = async (email) => { 
    let response = await usersAxios.post('/fetch-token',JSON.stringify({
        email: email
    }));
    
    if (response.status == 200) { 
        return response.data;
    }
    else { 
        throw response.data
    }
 }

// const removeAccount = async (id) => { return true }

export default { 
    login, 
    register, 
    fetchMqttToken
}