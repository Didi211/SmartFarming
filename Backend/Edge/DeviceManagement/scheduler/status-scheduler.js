import cron from 'node-cron';
import logic from '../logic/logic.js';
import constants from '../utils/constants.js';

export const startStatusScheduler = () => { 
    cron.schedule('* * * * *', async () => { 
        console.log('Start checking device health statuses.');
        let allGood = true;
        // check device statuses from edgex
        let [sensors, actuators] = await Promise.all([
            logic.fetchAllDevices(process.env.SENSOR_PROFILE_NAME),
            logic.fetchAllDevices(process.env.ACTUATOR_PROFILE_NAME),
        ]);
        let allDevices = [...sensors, ...actuators];
        if (allDevices.length === 0) { 
            console.log('No devices available.');
            return;
        }
        for (let device of allDevices) { 
            let status, action;
            if (device.operatingState === constants.STATE_UP) { 
                status = constants.ONLINE;
                action = 'started';
            }
            else { 
                status = constants.OFFLINE;
                action = 'stopped';
            }
            let data = { 
                message: `Device [${device.name}] ${action} working.`,
                metadata: { 
                    deviceId: device.tags.mongoId,
                    status: status,
                }
            }
            await logic.sendStatusUpdate(data);
            if (status === constants.OFFLINE) { 
                allGood = false;
                console.log(`Alert: ${data.message}`);
            }
        }
        if (allGood) { 
            console.log('All devices are UP and running.');
        }
    })
}

