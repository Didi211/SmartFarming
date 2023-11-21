import logic from '../logic/device-management-logic.js';
import { handleApiError } from '../utils/error-handler.js';
import responseDtoMapper from '../utils/response-dto-mapper.js';

const alert = async (req, res) => { 
    let message = req.body.message;
    try { 
        let result = await logic.sendAlertToCloud(message);
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

export default { 
    alert
}