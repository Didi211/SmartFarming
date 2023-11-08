import logic from '../logic/user-management-logic.js';

const register = async (req, res) => { 
    try { 
        let result = await logic.register(req.body);
        res.status(200).send(result);
    }
    catch(error) { 
        res.status(500).send(error);
    }
}

const login = async (req, res) => { 
    try { 
        let username = req.body?.username;
        let password = req.body?.password;
        let result = await logic.login(username, password);
        res.status(200).send(result);
    }
    catch(error) { 
        res.status(500).send(error);
    }
}

const fetchMqttToken = async (req, res) => { 
    
    try { 
        let username = req.body?.username;
        let password = req.body?.password;
        let result = await logic.fetchMqttToken(username, password);
        res.status(200).send(result);
    }
    catch(error) { 
        res.status(500).send(error);
    }
 }

export default {
    register,
    login,
    fetchMqttToken
}