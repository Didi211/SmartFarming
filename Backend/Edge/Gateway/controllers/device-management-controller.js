import logic from '../logic/device-management-logic.js';
import { handleApiError } from '../utils/error-handler.js';
import responseDtoMapper from '../utils/response-dto-mapper.js';

const alert = async (req, res) => { 
    // let message = req.body.message;
    let data = req.body
    try { 
        let result = await logic.sendAlertToCloud(data);
        let responseDto = responseDtoMapper.succesfullResponseDto(
            200,
            "Alerting user successfull.",
            result
        )
        res.status(responseDto.status).send(responseDto);
    }
    catch(error) { 
        handleApiError(res, error);
    }
}

const updateState = async (req, res) => { 
     let data = req.body; // { deviceId, state: [ON,OFF]}
     try { 
         let result = await logic.sendStateUpdateToCloud(data);
         let responseDto = responseDtoMapper.succesfullResponseDto(
             200,
             "Sending actuator state to cloud successfull.",
             result
         )
         res.status(responseDto.status).send(responseDto);
     }
     catch(error) { 
        handleApiError(res, error);
     }
}

export default { 
    alert,
    updateState
}