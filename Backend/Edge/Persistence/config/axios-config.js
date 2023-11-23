import { Axios } from 'axios';

const edgeGatewayUrl = process.env.EDGE_GATEWAY_URL



const edgeGatewayAxios = new Axios({
    baseURL: `${edgeGatewayUrl}`,
    withCredentials: false,
    headers: { 
        Accept: "application/json",
        "Content-Type": "application/json"
    }
});

export { 
    edgeGatewayAxios
}