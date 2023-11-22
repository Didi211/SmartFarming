import cron from 'node-cron';
import logic from '../logic/logic.js';

export const startAggregationScheduler = () => { 
    cron.schedule('*/10 * * * *', async () => { 
        let data = await logic.sendAggregatedData();
        console.log('Agreggating sensor data:', data);
    })
}