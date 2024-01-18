import { Axios } from 'axios';


const influxUrl = process.env.INFLUX_DB_URL


let influxAxios = new Axios({
    baseURL: `${influxUrl}`,
    withCredentials: false,
    headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
        Authorization: `Token ${process.env.BUCKET_SENSOR_DATA_API_TOKEN}`
    },
    
})




export  { 
    influxAxios
}