import logic from '../logic/logic.js';
import { handleApiError } from '../utils/error-handler.js';
import responseDtoMapper from '../utils/response-dto-mapper.js';

const syncData = async (req, res) => { 
    let userId = req.headers['user-id'];
    let data = req.body;
    try { 
        let result = await logic.syncData(userId, data);
        let responseDto = responseDtoMapper.succesfullResponseDto(
            200,
            "Syncing data successfull.",
            result
        )
        res.status(responseDto.status).send(responseDto);
    }
    catch(error) { 
        handleApiError(res, error);
    }
}

const getHourlyData = async (req, res) => {
    let userId = req.headers['user-id'];
    let sensorId = req.params.id;
    let startDate = req.body.startDate;
    let endDate = req.body.endDate;
    try { 
        let result = await logic.getHourlyData(userId, sensorId, startDate, endDate);
        let responseDto = responseDtoMapper.succesfullResponseDto(
            200,
            "Fetching hourly data successfull.",
            result
        )
        res.status(responseDto.status).send(responseDto);
    }
    catch(error) { 
        handleApiError(res, error);
    }
}

export default { 
    syncData,
    getHourlyData
}