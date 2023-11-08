import logic from '../logic/user-management-logic.js';

const register = async (req, res) => { 
    logic.register(req.body);
}

const login = async (req, res) => { 
    logic.login(req.body);
}

const fetchMqttToken = async (req, res) => { 
    logic.fetchMqttToken(req.body);
 }

export default {
    register,
    login,
    fetchMqttToken
}