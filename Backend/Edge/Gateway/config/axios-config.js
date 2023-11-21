import { Axios } from 'axios';

const analyticsUrl = process.env.ANALYTICS_SERVICE_URL
const cloudGatewayUrl = process.env.CLOUD_GATEWAY_URL

const analyticsAxios = new Axios({
    baseURL: `${analyticsUrl}`,
    withCredentials: false,
    headers: { 
        Accept: "application/json",
        "Content-Type": "application/json"
    }
});

const cloudGatewayAxios = new Axios({
    baseURL: `${cloudGatewayUrl}`,
    withCredentials: false,
    headers: { 
        Accept: "application/json",
        "Content-Type": "application/json"
    }
});

export { 
    analyticsAxios,
    cloudGatewayAxios
}