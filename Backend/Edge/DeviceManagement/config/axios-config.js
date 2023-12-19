import { Axios } from 'axios';
import { v4 } from 'uuid'

const edgeGatewayUrl = process.env.EDGE_GATEWAY_URL;
const edgexCoreMetadataUrl = process.env.EDGEX_CORE_METADATA_URL;

const edgeGatewayAxios = new Axios({
    baseURL: `${edgeGatewayUrl}`,
    withCredentials: false,
    headers: { 
        Accept: "application/json",
        "Content-Type": "application/json"
    }
});

const edgexCoreMetadataAxios = new Axios({
    baseURL: `${edgexCoreMetadataUrl}`,
    withCredentials: false,
    headers: { 
        Accept: "application/json",
        "Content-Type": "application/json",
        "X-Correlation-ID": v4()
    }
});

export { 
    edgeGatewayAxios,
    edgexCoreMetadataAxios,
}