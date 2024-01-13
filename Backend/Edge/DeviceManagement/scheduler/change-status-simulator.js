import cron from 'node-cron'
import logic from '../logic/logic.js';

export const startChangeStatusScheduler = () => { 
    // '*/5 * * * *' every 5 minutes 
    // '* */2 * * *' every 2 hours 
    cron.schedule('0 */2 * * *', async () => { 
        try {
            let [sensors, actuators] = await Promise.all([
                logic.fetchAllDevices(process.env.SENSOR_PROFILE_NAME),
                logic.fetchAllDevices(process.env.ACTUATOR_PROFILE_NAME),
            ]);
            let allDevices = [...sensors, ...actuators];
            let count = allDevices.length;
            if (count === 0) { 
                return;
            }
            console.log('Simulating changing device statuses in Edgex.');
            let indexes = [];
            let numOfIndexes = Math.min(3, count);
            for (let i = 0; i< numOfIndexes; i++) { 
                let random = Math.random() % count * count;
                let index = Math.floor(random);
                indexes.push(index);
            }   
            for (let i of indexes) {  
                let device = allDevices[i]
                let deviceId = device.id
                let newStatus = await logic.updateDeviceStatusOnEdgex(deviceId, device.operatingState);
                console.log(`Device ${device.name} changed status from ${device.operatingState} to ${newStatus}.`);
            }
            
        } catch (error) {
            console.log('Error in Change Status simulator', error);
        }
        
    })
}