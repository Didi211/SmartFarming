import cron from 'node-cron';
import logic from '../logic/logic.js';

export const startAggregationScheduler = () => { 
    cron.schedule('*/10 * * * *', async () => { 
        try {
            let data = await logic.sendAggregatedData();
            if (data.length >0) { 
                console.log('Agreggating sensor data:', data);
            }
        } catch (error) {
            console.log(error);
        }
    })
}