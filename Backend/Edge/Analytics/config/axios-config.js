import { Axios } from 'axios';

const edgexMetadataUrl = process.env.EDGEX_CORE_METADATA_URL

const edgexMetadataAxios = new Axios({
    baseURL: `${edgexMetadataUrl}`,
    withCredentials: false,
    headers: { 
        Accept: "application/json",
        "Content-Type": "application/json"
    }
});

export { 
    edgexMetadataAxios
}