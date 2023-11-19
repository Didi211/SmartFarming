import logic from "../logic/logic.js";
import { handleApiError } from "../utils/error-handler.js";
import responseDtoMapper from "../utils/response-dto-mapper.js";

const register = async (req, res) => { 
    try { 
        let result = await logic.register(req.body);
        let responseDto = responseDtoMapper.succesfullResponseDto(
            200,
            "Register successfull.",
            result
        );
        res.status(responseDto.status).send(responseDto);
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
        let responseDto = responseDtoMapper.succesfullResponseDto(
            200,
            "Login successfull.",
            result
        );
        res.status(responseDto.status).send(responseDto);
    }
    catch(error) { 
        handleApiError(res, error);
    }
}

const fetchMqttToken = async (req, res) => { 
    
    try { 
        let email = req.body?.email;
        let result = await logic.fetchMqttToken(email);
        let responseDto = responseDtoMapper.succesfullResponseDto(
            200,
            "Token fetched successfully.",
            result
        );
        res.status(responseDto.status).send(responseDto);
    }
    catch(error) { 
        handleApiError(res, error);
    }
}

const isUserExisting = async (req, res) => { 
    
    try { 
        let userId = req.params.id;
        let result = await logic.isUserExisting(userId);
        let responseDto = responseDtoMapper.succesfullResponseDto(
            200,
            `User ${!result ? 'not' : ''} existing`,
            result
        );
        res.status(responseDto.status).send(responseDto);
    }
    catch(error) { 
        handleApiError(res, error);
    }
}

const getIdByMqttToken = async (req, res) => {
    try { 
        let token = req.params.token;
        let result = await logic.getIdByMqttToken(token);
        let responseDto = responseDtoMapper.succesfullResponseDto(
            200,
            `Fetching id successfull.`,
            result
        );
        res.status(responseDto.status).send(responseDto);
        
    }
    catch(error) { 
        handleApiError(res, error);
    }

}

export default { 
    login,
    register,
    fetchMqttToken,
    isUserExisting,
    getIdByMqttToken
}