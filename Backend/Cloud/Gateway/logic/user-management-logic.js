import {usersAxios } from '../config/axios-config.js';


const login = async (email, password) => { 
    let response = await usersAxios.post('/login', JSON.stringify({
        email: email,
        password: password
    }));
    
    if (response.status == 200) { 
        return JSON.parse(response.data);
    }
    else { 
        throw response.data
    }
}

const register = async (user) => { 
    let response = await usersAxios.post('/register', JSON.stringify(user));

    if (response.status == 200) { 
        return JSON.parse(response.data);
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
        return JSON.parse(response.data);
    }
    else { 
        throw response.data
    }
}

const isUserExisting = async (userId) => { 
    let response = await usersAxios.get(`/${userId}/exists`);
    if (response.status == 200) { 
        return JSON.parse(response.data);
    }
    throw response.data
}

const getIdByMqttToken = async (mqttToken) => { 
    let response = await usersAxios.get(`/get-id/${mqttToken}`);
    if (response.status == 200) { 
        return JSON.parse(response.data);
    }
    throw response.data
}

const get = async (id) => { 
    let response = await usersAxios.get(`/${id}`);
    if (response.status == 200) { 
        return JSON.parse(response.data);
    }
    throw response.data;
}



export default {
    register,
    login,
    fetchMqttToken,
    isUserExisting,
    getIdByMqttToken,
    get
}