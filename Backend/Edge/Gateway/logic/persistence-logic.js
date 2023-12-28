import { cloudGatewayAxios } from '../config/axios-config.js';
import { MqttTokenManager } from '../utils/mqtt-token-manager.js';

const saveDataToCloud = async (data) => { 
    let response = await cloudGatewayAxios.post(`/sensor-data/sync`, JSON.stringify({
        data: data
    }), { 
        headers: { 
            'mqtt-token': MqttTokenManager.Token()
        }
    });
    if (response.status == 200) { 
        console.log(`Synced data to Cloud:`, data);
        return null;
    }
    else { 
        throw response.data;
    }
}

export default { 
    saveDataToCloud
}