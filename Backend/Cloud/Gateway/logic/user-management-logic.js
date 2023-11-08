import service from "../services/user-management-service.js";

const login = async (username, password) => { 
    service.login(username, password);
}

const register = async (user) => { 
    service.register(user);
}

const fetchMqttToken = async (username, password) => { 
    service.fetchMqttToken(username, password);
}

export default {
    register,
    login,
    fetchMqttToken
}