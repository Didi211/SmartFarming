import { Axios } from 'axios';
import { v4 } from 'uuid'

const edgexDeviceRestUrl = process.env.EDGEX_DEVICE_REST_URL

const edgexDeviceRestAxios = new Axios({
    baseURL: `${edgexDeviceRestUrl}`,
    withCredentials: false,
    headers: { 
        Accept: "application/json",
        "Content-Type": "application/json",
        "X-Correlation-ID": v4()
    }
});

export { 
    edgexDeviceRestAxios
}