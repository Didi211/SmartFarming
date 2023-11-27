import cron from 'node-cron';
import logic from '../logic/logic.js';

export const startStatusScheduler = () => { 
    cron.schedule('* * * * *', async () => { 
        // check device statuses from edgex
        let shouldAlert = true
        let data = { 
            message: `Sensor with ID [656096eac1afa1de958e6d61] stopped working.`,
            metadata: { 
                sensorId: '656096eac1afa1de958e6d61',
                status: 'OFFLINE'
            }
        }
        if (shouldAlert) { 
            await logic.sendAlert(data)
        }
    })
}