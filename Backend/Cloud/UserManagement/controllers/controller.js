import logic from "../logic/logic.js";

const register = async (req, res) => { 
    try { 
        let result = await logic.register(req.body);
        res.status(200).send(result);
    }
    catch(error) { 
        console.log(error);
        res.status(500).send(error);
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
        console.log(error);
        res.status(500).send(error);
    }
}

const fetchMqttToken = async (req, res) => { 
    
    try { 
        let email = req.body?.email;
        let result = await logic.fetchMqttToken(email);
        res.status(200).send(result);
    }
    catch(error) { 
        console.log(error);
        res.status(error.status || 500).send(error);
    }
}

export default { 
    login,
    register,
    fetchMqttToken
}