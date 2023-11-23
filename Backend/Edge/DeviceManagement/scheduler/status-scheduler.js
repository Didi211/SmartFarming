import cron from 'node-cron';
import logic from '../logic/logic.js';

export const startStatusScheduler = () => { 
    cron.schedule('* * * * *', async () => { 
        // check device statuses from edgex
        let shouldAlert = true
        let data = { 
            message: `Sensor with ID [655613dab90f7a70bb421a26] stopped working.`,
            metadata: { 
                sensorId: '655613dab90f7a70bb421a26',
                status: 'OFFLINE'
            }
        }
        console.log(data);
        if (shouldAlert) { 
            await logic.sendAlert(data)
        }
    })
}