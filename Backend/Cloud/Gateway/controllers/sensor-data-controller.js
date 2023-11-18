import logic from "../logic/sensor-data-logic.js";
import { handleApiError } from "../utils/error-handler.js";

const saveData = async (req, res) => {
    try { 
        let userId = req.headers['user-id'];
        let data = req.body.data;
        let result = await logic.saveSensorData(userId, data);
        res.status(result.status).send(result)
    }
    catch(error) { 
        handleApiError(res, error);
    }
}

const getHistoryData = async (req, res) => { 
    try { 

        let userId = req.headers['user-id'];
        let sensorId = req.params.id;
        let period = req.query.period;
        let startDate = req.body.startDate;
        let endDate = req.body.endDate;
        
        let result = await logic.getHistoryData({ 
            userId: userId,
            sensorId: sensorId,
            period: period,
            startDate: startDate,
            endDate: endDate
        });
        res.status(result.status).send(result);
    } 
    catch(error) { 
        handleApiError(res, error);
    }  
}



export default { 
    saveData,
    getHistoryData
}