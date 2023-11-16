import logic from '../logic/user-management-logic.js';
import { handleApiError } from '../utils/error-handler.js';
const register = async (req, res) => { 
    try { 
        let result = await logic.register(req.body);
        res.status(result.status).send(result);
    }
    catch(error) { 
        handleApiError(res, error);
    }
}

const login = async (req, res) => { 
    try { 
        let email = req.body?.email;
        let password = req.body?.password;
        // add pass encryption
        let result = await logic.login(email, password);
        res.status(result.status).send(result);
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

const isUserExisting = async (req, res) => { 
    try { 
        let userId = req.paramas.id;
        let result = await logic.isUserExisting(userId);
        res.status(200).send(result);
    }
    catch(error) { 
        handleApiError(res, error);
    }
}



export default {
    register,
    login,
    fetchMqttToken,
    isUserExisting
}