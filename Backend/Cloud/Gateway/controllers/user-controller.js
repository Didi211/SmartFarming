import logic from '../logic/user-management-logic.js';
import { handleApiError } from '../utils/error-handler.js';
const register = async (req, res) => { 
    try { 
        let result = await logic.register(req.body);
        res.status(200).send(result);
    }
    catch(error) { 
        handleApiError(res, error);
    }
}

const login = async (req, res) => { 
    try { 
        let email = req.body?.email;
        let password = req.body?.password;
        let result = await logic.login(email, password);
        res.status(200).send(result);
    }
    catch(error) { 
        handleApiError(res, error);
    }
}

const fetchMqttToken = async (req, res) => { 
    
    try { 
        let email = req.body?.email;
        let result = await logic.fetchMqttToken(email);
        res.status(200).send(result);
    }
    catch(error) { 
        handleApiError(res, error);
    }
}



export default {
    register,
    login,
    fetchMqttToken
}