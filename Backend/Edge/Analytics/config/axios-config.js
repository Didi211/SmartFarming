import { Axios } from 'axios';
import { v4 } from 'uuid'

const edgexMetadataUrl = process.env.EDGEX_CORE_METADATA_URL;

let deviceSimulatorAxios;
if (process.env.DEVICE_SIMULATOR_ENABLED) { 
    let deviceSimulatorUrl = process.env.DEVICE_SIMULATOR_URL;
    if (deviceSimulatorUrl) { 
        deviceSimulatorAxios = new Axios({
            baseURL: `${deviceSimulatorUrl}`,
            withCredentials: false,
            headers: { 
                Accept: "application/json",
                "Content-Type": "application/json",
            }
        });
    }
    else { 
        deviceSimulatorAxios = null;
    }
}

const edgexMetadataAxios = new Axios({
    baseURL: `${edgexMetadataUrl}`,
    withCredentials: false,
    headers: { 
        Accept: "application/json",
        "Content-Type": "application/json",
        "X-Correlation-ID": v4()
    }
});



export { 
    edgexMetadataAxios,
    deviceSimulatorAxios
}