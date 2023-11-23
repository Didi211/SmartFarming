import { edgeGatewayAxios } from "../config/axios-config.js"

const sendAlert = async (data) => { 
    let response = await edgeGatewayAxios.post('/alert', JSON.stringify(data));
    if (response.status == 200) { 
        return JSON.parse(response.data);
    }
    else { 
        throw response.data;
    }
}

export default { 
    sendAlert
}