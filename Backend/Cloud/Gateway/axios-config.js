import { Axios } from 'axios';

const edgeGatewayUrl = process.env.EDGE_GATEWAY_URL;
const persistenceUrl = process.env.PERSISTENCE_URL;

let edgeGatewayInstance = new Axios({
    baseURL: `${edgeGatewayUrl}`
});
let persistenceInstance = new Axios({
    baseURL: `${persistenceUrl}`
});

export const edgeGateway = edgeGatewayInstance
export const persistence = persistenceInstance