import logic from '../logic/device-logic.js';
import { handleApiError } from '../utils/error-handler.js';
import responseDtoMapper from '../utils/response-dto-mapper.js';

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

const update = async (req, res) => { 
    let device = req.body;
    let id = req.params.id;
    try { 
        await logic.updateDevice(id, device);
        let responseDto = responseDtoMapper.succesfullResponseDto(
            200,
            "Updating device successfull.",
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

// const changeState = async (req, res) => { 
//     let id = req.params.id;
//     let state = req.body.state;
//     try { 
//         await logic.changeState(id, state);
//         let responseDto = responseDtoMapper.succesfullResponseDto(
//             200,
//             "Changing device state successfull.",
//         )
//         res.status(responseDto.status).send(responseDto);
//     }
//     catch(error) { 
//         handleApiError(res, error);
//     }

// }

const changeStatus = async (req, res) => { 
    let id = req.params.id;
    let status = req.body.status;
    try { 
        await logic.changeStatus(id, status);
        let responseDto = responseDtoMapper.succesfullResponseDto(
            200,
            "Changing device status successfull.",
        )
        res.status(responseDto.status).send(responseDto);
    }
    catch(error) { 
        handleApiError(res, error);
    }

}

export default { 
    add,
    update,
    remove,
    // changeState, 
    changeStatus
}