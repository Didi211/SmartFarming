import { Axios } from 'axios';

const sensorDataUrl = process.env.SENSOR_DATA_SERVICE_URL;
const deviceManagementUrl = process.env.DEVICE_MANAGEMENT_SERVICE_URL;
const notificationsAUrl= process.env.NOTIFICATION_SERVICE_URL;
const usersUrl = process.env.USER_MANAGEMENT_SERVICE_URL;


let sensorDataAxios = new Axios({
    baseURL: `${sensorDataUrl}`
})
let deviceManagementAxios = new Axios({
    baseURL: `${deviceManagementUrl}`
})
let notificationsAxios = new Axios({
    baseURL: `${notificationsAUrl}`
})
let usersAxios = new Axios({
    baseURL: `${usersUrl}`
})


export default { 
    sensorDataAxios, 
    deviceManagementAxios,
    notificationsAxios,
    usersAxios
}