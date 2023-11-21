import { analyticsAxios } from "../config/axios-config.js";

const addDevice = async (device) => { 
    let response = await analyticsAxios.post('/devices/', JSON.stringify({device}));
    if (response.status == 200) { 
        return JSON.parse(response.data);
    }
    else { 
        throw response.data;
    }
}

const updateDevice = async (device) => { 
    let response = await analyticsAxios.put(`/devices/${device.id}`, JSON.stringify({device}));
    if (response.status == 200) { 
        return JSON.parse(response.data);
    }
    else { 
        throw response.data;
    }
}

const removeDevice = async (device) => { 
    let response = await analyticsAxios.delete(`/devices/${device.id}`);
    if (response.status == 200) { 
        return JSON.parse(response.data);
    }
    else { 
        throw response.data;
    }
}

const addRule = async (rule) => { 
    let response = await analyticsAxios.post('/rules/', JSON.stringify({rule}));
    if (response.status == 200) { 
        return JSON.parse(response.data);
    }
    else { 
        throw response.data;
    }
}

const updateRule = async (rule) => { 
    let response = await analyticsAxios.put(`/rules/${rule.id}`, JSON.stringify({rule}));
    if (response.status == 200) { 
        return JSON.parse(response.data);
    }
    else { 
        throw response.data;
    }
}

const removeRule = async (rule) => { 
    let response = await analyticsAxios.delete(`/rules/${rule.id}`);
    if (response.status == 200) { 
        return JSON.parse(response.data);
    }
    else { 
        throw response.data;
    }
}

export default { 
    addDevice,
    updateDevice,
    removeDevice,
    addRule,
    updateRule,
    removeRule
}