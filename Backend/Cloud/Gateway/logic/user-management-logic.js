import service from "../services/user-management-service.js";
import userValidator from "../utils/user-validator.js";
const login = async (username, password) => { 
    let result = userValidator.loginValidate(username, password);
    if (result != "") { 
        throw { 
            code: 400,
            message: 'Validation failed.',
            details: result
        };
    }
    // make jwt
    return await service.login(username, password);
}

const register = async (user) => { 
    let result = userValidator.registerValidate(user);
    if (result != "") { 
        throw { 
            code: 400,
            message: 'Validation failed.',
            details: result
        };
    }
    // make jwt 
    return await service.register(user);
}

const fetchMqttToken = async (username, password) => { 
    let result = userValidator.loginValidate(username, password);
    if (result != "") { 
        throw { 
            code: 400,
            message: 'Validation failed.',
            details: result
        };
    }
    return await service.fetchMqttToken(username, password);
}

export default {
    register,
    login,
    fetchMqttToken
}