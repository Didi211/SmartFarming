import service from "../services/user-management-service.js";

// call remote service
const login = async (email, password) => { 
    return await service.login(email, password);
}

const register = async (user) => { 
    return await service.register(user);
}

const fetchMqttToken = async (email, password) => { 
    return await service.fetchMqttToken(email, password);
}

export default {
    register,
    login,
    fetchMqttToken
}