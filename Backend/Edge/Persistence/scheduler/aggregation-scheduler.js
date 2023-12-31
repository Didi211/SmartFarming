import cron from 'node-cron';
import logic from '../logic/logic.js';

export const startAggregationScheduler = () => { 
    cron.schedule('*/10 * * * *', async () => { 
        try {
            console.log('Start aggregating sensor data');
            let data = await logic.sendAggregatedData();
            if (data.length >0) { 
                console.log('Aggregated sensor data:', data);
            }
            else { 
                console.log('No sensors found.');
            }
        } catch (error) {
            console.log(error);
        }
    })
}