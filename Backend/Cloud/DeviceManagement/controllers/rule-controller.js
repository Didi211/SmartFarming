import logic from '../logic/rule-logic.js';
import { handleApiError } from '../utils/error-handler.js';
import responseDtoMapper from '../utils/response-dto-mapper.js';


const getById = async (req, res) => {
    let id = req.params.id;
    try { 
        let result = await logic.getById(id);
        let responseDto = responseDtoMapper.succesfullResponseDto(
            200,
            "Fetching rule successfull.",
            result
        )
        res.status(responseDto.status).send(responseDto);
    }
    catch(error) { 
        handleApiError(res, error);
    }
}

const getByDeviceId = async (req, res) => {
    let id = req.params.id;
    try { 
        let result = await logic.getByDeviceId(id);
        let responseDto = responseDtoMapper.succesfullResponseDto(
            200,
            "Fetching rule successfull.",
            result
        )
        res.status(responseDto.status).send(responseDto);
    }
    catch(error) { 
        handleApiError(res, error);
    }
}

const getByUserId = async (req, res) => {
    let id = req.params.id;
    try { 
        let result = await logic.getByUserId(id);
        let responseDto = responseDtoMapper.succesfullResponseDto(
            200,
            "Fetching rules successfull.",
            result
        )
        res.status(responseDto.status).send(responseDto);
    }
    catch(error) { 
        handleApiError(res, error);
    }
}

const add = async (req, res) => { 
    let rule = req.body;
    try { 
        let result = await logic.addRule(rule);
        let responseDto = responseDtoMapper.succesfullResponseDto(
            200,
            "Adding rule successfull.",
            result
        )
        res.status(responseDto.status).send(responseDto);
    }
    catch(error) { 
        handleApiError(res, error);
    }
}

const update = async (req, res) => { 
    let rule = req.body;
    let id = req.params.id;
    try { 
        let result = await logic.updateRule(id, rule);
        let responseDto = responseDtoMapper.succesfullResponseDto(
            200,
            "Updating rule successfull.",
            result
        )
        res.status(responseDto.status).send(responseDto);
    }
    catch(error) { 
        handleApiError(res, error);
    }
}

const remove = async (req, res) => { 
    let id = req.params.id;
    try { 
        await logic.removeRule(id);
        let responseDto = responseDtoMapper.succesfullResponseDto(
            200,
            "Removing rule successfull.",
        )
        res.status(responseDto.status).send(responseDto);
    }
    catch(error) { 
        handleApiError(res, error);
    }
}

export default { 
    getById,
    getByDeviceId,
    getByUserId,
    add,
    update,
    remove
}