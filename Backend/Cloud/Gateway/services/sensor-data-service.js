const saveSensorData = async (sensorId, data) => { return true }

const getHourlyHistory = async (sensorId, startDate, endDate) => { return [] }

const getMonthlyHistory = async (sensorId, startDate, endDate) => { return [] }

const getYearlyHistory = async (sensorId, startDate, endDate) => { return [] }

export default { 
    saveSensorData, 
    getHourlyHistory,
    getMonthlyHistory,
    getYearlyHistory
}