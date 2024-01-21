import deviceLogic from '../logic/device-logic.js';
import logic from '../logic/device-logic.js';
import { handleApiError } from '../utils/error-handler.js';
import responseDtoMapper from '../utils/response-dto-mapper.js';

const getAll = async (req, res) => { 
    let filter = req.query.type;
    let userId = req.params.userId;
    try { 
        let result = await logic.getAll(filter, userId);
        if (result.length > 0) { 
            let responseDto = responseDtoMapper.succesfullResponseDto(
                200,
                "Devices fetched",
                result
            )
            res.status(responseDto.status).send(responseDto);
        }
        else { 
            let responseDto = responseDtoMapper.succesfullResponseDto(
                200, // 204 has no response data
                "No existing devices.",
                result
            )
            res.status(responseDto.status).send(responseDto);
        }
    }
    catch(error) { 
        handleApiError(res, error);
    }
}

const getById = async (req, res) => { 
    let id = req.params.id;
    try { 
        let result = await logic.findById(id);
        let responseDto = responseDtoMapper.succesfullResponseDto(
            200,
            "Fetching device successfull.",
            result
        )
        res.status(responseDto.status).send(responseDto);
    }
    catch(error) { 
        handleApiError(res, error);
    }
}

const add = async (req, res) => { 
    let device = req.body;
    try { 
        let result = await logic.addDevice(device);
        let responseDto = responseDtoMapper.succesfullResponseDto(
            200,
            "Creating device successfull.",
            result
        )
        res.status(responseDto.status).send(responseDto);
    }
    catch(error) { 
        handleApiError(res, error);
    }
}
const getAvailableDevices = async (req, res) => {
    try {
        let userId = req.params.userId;
        let type = req.params.type;
        let result = await deviceLogic.getAvailableDevices(userId, type);
        let responseDto = responseDtoMapper.succesfullResponseDto(
            200,
            "Fetching available devices successfull.",
            result
        )
        res.status(responseDto.status).send(responseDto);
    } catch (error) {
        handleApiError(res, error)
    }
}

const update = async (req, res) => { 
    let device = req.body;
    let id = req.params.id;
    try { 
        let result = await logic.updateDevice(id, device);
        let responseDto = responseDtoMapper.succesfullResponseDto(
            200,
            "Updating device successfull.",
            result
        )
        res.status(responseDto.status).send(responseDto);
    }
    catch(error) { 
        handleApiError(res, error);
    }
}

const updateState = async (req, res) => { 
    let state = req.body.state;
    let id = req.params.id;
    try { 
        await logic.updateDeviceState(id, state);
        let responseDto = responseDtoMapper.succesfullResponseDto(
            200,
            "Updating device state successfull.",
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
        await logic.removeDevice(id);
        let responseDto = responseDtoMapper.succesfullResponseDto(
            200,
            "Removing device successfull.",
        )
        res.status(responseDto.status).send(responseDto);
    }
    catch(error) { 
        handleApiError(res, error);
    }
}

export default {
    getAll,
    getAvailableDevices,
    getById,
    add,
    update,
    updateState,
    remove
}