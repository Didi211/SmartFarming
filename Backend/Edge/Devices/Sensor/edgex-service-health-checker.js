import { edgexDeviceRestAxios } from "./config/axios-config.js";

const waitForRestDeviceService = async () => {
    let edgexDeviceServiceReady = false; 
    while (!edgexDeviceServiceReady) {
        try {
            let ready = await edgexDeviceRestAxios.get('/ping');
            if (ready.status === 200) {
                edgexDeviceServiceReady = true; // Service is ready
            } else {
                await new Promise(resolve => setTimeout(resolve, 1000)); // Wait for one second before the next attempt
            }
        } catch (error) {
          // Handle errors, e.g., log or throw an exception
          console.error("Error while checking EdgeX Rest Device Service status:", error);
          await new Promise(resolve => setTimeout(resolve, 1000)); // Wait for one second before the next attempt
        }
    }
}

export default { 
    waitForRestDeviceService
}