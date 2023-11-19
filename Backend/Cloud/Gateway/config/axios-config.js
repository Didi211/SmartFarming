import { Axios } from 'axios';


const sensorDataUrl = process.env.SENSOR_DATA_SERVICE_URL;
const deviceManagementUrl = process.env.DEVICE_MANAGEMENT_SERVICE_URL;
const notificationsAUrl= process.env.NOTIFICATION_SERVICE_URL;
const usersUrl = process.env.USER_MANAGEMENT_SERVICE_URL;


let sensorDataAxios = new Axios({
    baseURL: `${sensorDataUrl}`,
    withCredentials: false,
    headers: {
        Accept: "application/json",
        "Content-Type": "application/json"
    }
})
let deviceManagementAxios = new Axios({
    baseURL: `${deviceManagementUrl}`,
    withCredentials: false,
    headers: {
        Accept: "application/json",
        "Content-Type": "application/json"
    }
})
let notificationsAxios = new Axios({
    baseURL: `${notificationsAUrl}`,
    withCredentials: false,
    headers: {
        Accept: "application/json",
        "Content-Type": "application/json"
    }
})
let usersAxios = new Axios({
    baseURL: `${usersUrl}`,
    withCredentials: false,
    headers: {
        Accept: "application/json",
        "Content-Type": "application/json"
    }
})


export  { 
    sensorDataAxios, 
    deviceManagementAxios,
    notificationsAxios,
    usersAxios
}