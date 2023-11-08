import logic from "../logic/sensor-data-logic.js";

const saveData = async (req, res) => {
    logic.saveSensorData(req.params.id, req.body);
}

const getHourlyHistory = async (req, res) => { 
    let sensorId = req.params.id;
    let startDate = req.body.startDate;
    let endDate = req.body.endDate;
    return await logic.getHourlyHistory(sensorId, startDate, endDate);
}

const getMonthlyHistory = async (req, res) => { 
    let sensorId = req.params.id;
    let startDate = req.body.startDate;
    let endDate = req.body.endDate;
    return await logic.getMonthlyHistory(sensorId, startDate, endDate);
}

const getYearlyHistory = async (req, res) => { 
    let sensorId = req.params.id;
    let startDate = req.body.startDate;
    let endDate = req.body.endDate;
    return await logic.getYearlyHistory(sensorId, startDate, endDate);
}

export default { 
    saveData,
    getHourlyHistory,
    getMonthlyHistory,
    getYearlyHistory
}