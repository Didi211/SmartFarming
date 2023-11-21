import { cloudGatewayAxios } from "../config/axios-config.js";

export const fetchMqttToken = async (email) => { 
    let response = await cloudGatewayAxios.post(`/users/fetch-token`, JSON.stringify({
        email: email
    }));
    if (response.status == 200) { 
        return JSON.parse(response.data).details;
    }
    else { 
        throw response.data;
    }
}