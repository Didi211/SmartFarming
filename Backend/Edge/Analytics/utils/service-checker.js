import axios from 'axios';

export const waitForServiceToBeReady = async (name, url) => {
    let serviceReady = false; 
    while (!serviceReady) {
        try {
          let ready = await axios.get(`${url}/ping`);
          if (ready.status === 200) {
            serviceReady = true; // Service is ready
          } else {
            await new Promise(resolve => setTimeout(resolve, 1000)); // Wait for one second before the next attempt
          }
        } catch (error) {
          // Handle errors, e.g., log or throw an exception
          console.error(`Error while checking ${name} service status:`, error);
          await new Promise(resolve => setTimeout(resolve, 1000)); // Wait for one second before the next attempt
        }
    }
}