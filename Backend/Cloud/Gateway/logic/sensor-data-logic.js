import service from '../services/sensor-data-service.js';

const saveSensorData = async (sensorId, data) => { 
    await service.saveSensorData(sensorId, data);
}

const getHourlyHistory = async (sensorId, startDate, endDate) => { 
    return await service.getHourlyHistory(sensorId, startDate, endDate);
}

const getMonthlyHistory = async (sensorId, startDate, endDate) => { 
    return await service.getMonthlyHistory(sensorId, startDate, endDate);
}

const getYearlyHistory = async (sensorId, startDate, endDate) => { 
    return await service.getYearlyHistory(sensorId, startDate, endDate);
}



export default { 
    saveSensorData,
    getHourlyHistory,
    getMonthlyHistory,
    getYearlyHistory,
}