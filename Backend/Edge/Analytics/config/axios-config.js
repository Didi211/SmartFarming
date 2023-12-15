import { Axios } from 'axios';
import { v4 } from 'uuid'

const edgexMetadataUrl = process.env.EDGEX_CORE_METADATA_URL

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
    edgexMetadataAxios
}