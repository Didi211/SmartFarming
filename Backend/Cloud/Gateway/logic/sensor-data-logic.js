import { sensorDataAxios } from '../config/axios-config.js';

const saveSensorData = async (userId, data) => { 
    let response = await sensorDataAxios.post(`/sync`, { 
        data: data
    }, { 
        headers: { 
            'user-id': userId 
        }
    });
    if (response.status == 200) { 
        return JSON.parse(response.data);
    }
    else { 
        throw response.data;
    }
}

const getHistoryData = async (data) => { 
    let response = await sensorDataAxios.post(`/${data.sensorId}?period=${data.period}`, {
        startDate: data.startDate,
        endDate: data.endDate
    }, { 
        headers: { 
            'user-id': data.userId
        } 
    });

    if (response.status == 200) { 
        return JSON.parse(response.data);
    }
    else { 
        throw response.data;
    }
}





export default { 
    saveSensorData,
    getHistoryData
}