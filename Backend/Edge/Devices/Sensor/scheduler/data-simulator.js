import cron from 'node-cron';
import { SensorModel } from '../models/sensor-model.js';
import { edgexDeviceRestAxios } from '../config/axios-config.js';

export const startDataSimulation = () => {
    // generating data every minute
    cron.schedule('* * * * *', async () => { 
        console.log('Simulating sensor readings:');
        let sensors = []
        try {
            sensors = SensorModel.find();
            if (sensors.length === 0) { 
                console.log('No sensors available.');
                return;
            }
        } catch (error) {
            console.log('Error while fetching sensors from local-db. File is not found');
            console.log(error);
        }
        for (let sensor of sensors) {
            try { 
                // Simulate a change between -5 and 5
                let changeAmount = Math.random() * 5;
                
                // Apply the change based on whether it's a raise or not
                let newHumidity = sensor.pumpState
                    ? Math.min(sensor.lastReading + changeAmount, 100) // Ensure it doesn't go above 100
                    : Math.max(sensor.lastReading - changeAmount, 0); // Ensure it doesn't go below 0
                sensor.lastReading = newHumidity;
                console.log(`Sensor: [${sensor._id}] - Humidity: [${sensor.lastReading}]`);
                sensor.save();
        
                // send data to edgex 
                let response = await edgexDeviceRestAxios.post(`/resource/${sensor._id}/Humidity`,sensor.lastReading.toString());
                if (response.status == 200) { 
                    console.log(`Reading from sensor ${sensor._id} published to Edgex`);
                }
                else { 
                    throw response
                }
            }
            catch(err) { 
                console.log(`Error for sensor ${sensor._id}:`);
                console.log(err);
            }
        }
    })
}