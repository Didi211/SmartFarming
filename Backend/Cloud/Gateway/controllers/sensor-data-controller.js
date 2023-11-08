import logic from "../logic/sensor-data-logic.js";

const saveData = async (req, res) => {
    try { 
        await logic.saveSensorData(req.params.id, req.body);
        res.status(204).send()
    }
    catch(error) { 
        res.status(error.code || 500).send(error);
    }
}

const getHourlyHistory = async (req, res) => { 
    await performCall(logic.getHourlyHistory, { 
        id: req.params.id,
        startDate: req.body.startDate,
        endDate: req.body.endDate
    })
}

const getMonthlyHistory = async (req, res) => { 
    await performCall(logic.getMonthlyHistory, { 
        id: req.params.id,
        startDate: req.body.startDate,
        endDate: req.body.endDate
    })
}

const getYearlyHistory = async (req, res) => { 
    await performCall(logic.getYearlyHistory, { 
        id: req.params.id,
        startDate: req.body.startDate,
        endDate: req.body.endDate
    })
}

const performCall = async (func, parameters) => { 
    let sensorId = parameters.id;
    let startDate = parameters.startDate;
    let endDate = parameters.endDate;
    try { 
        let data = await func(sensorId, startDate, endDate);
        if (data.length > 0) { 
            res.status(200).send(data);
        }
        else { 
            res.status(204).send();
        }
    }
    catch(error) { 
        res.status(500).send(error);
    }
}

export default { 
    saveData,
    getHourlyHistory,
    getMonthlyHistory,
    getYearlyHistory
}