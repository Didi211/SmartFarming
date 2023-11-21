import logic from '../logic/persistence-logic.js';
import { handleApiError } from '../utils/error-handler.js';
import responseDtoMapper from '../utils/response-dto-mapper.js';

const saveData = async (req, res) => { 
    let data = req.body.data;
    try { 
        let result = await logic.saveDataToCloud(data);
        let responseDto = responseDtoMapper.succesfullResponseDto(
            200,
            "Syncing data to Cloud successfull.",
            result
        )
        res.status(responseDto.status).send(responseDto);
    }
    catch(error) { 
        handleApiError(res, error);
    }
}

export default { 
    saveData
}