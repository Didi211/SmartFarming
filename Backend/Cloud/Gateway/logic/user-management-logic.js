import {usersAxios } from '../axios-config.js';


// call remote service
const login = async (email, password) => { 
    let response = await usersAxios.post('/login', JSON.stringify({
        email: email,
        password: password
    }));
    
    if (response.status == 200) { 
        return response.data;
    }
    else { 
        throw response.data
    }
}

const register = async (user) => { 
    let response = await usersAxios.post('/register', JSON.stringify(user));

    if (response.status == 200) { 
        return response.data;
    }
    else { 
        throw response.data
    }
}

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

export default {
    register,
    login,
    fetchMqttToken
}