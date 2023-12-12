import { edgexMetadataAxios } from "./axios-config.js";
import { v4 } from 'uuid'

export const loadEdgexProfiles = async () => { 
    let data = [
        loadSensorProfile(),
        loadActuatorProfile()
    ]
    console.log(`Started loading profiles to EdgeX.`);
    try {
        let response = await edgexMetadataAxios.post('deviceprofile', JSON.stringify(data), { 
            headers: { 
                "X-Correlation-ID": v4()
            }
        });
        if (response.status != 207) { 
            throw response.data;
        }
        let arr = JSON.parse(response.data);
        console.log('Displaying results of loading profiles:');
        arr.forEach(res => { 
            console.log(res);
        });
    }
    catch(error) { 
        // will display error if device profile exists 
        // will happened beacuse existing check is not done
        console.log('Failed to load profiles to EdgeX');
        console.log(`Error: ${error}`);
    }
    
}

const loadSensorProfile = () => { 
    return {
        "requestId": v4(),
        "apiVersion": "v3",
        "profile": {
            "name": "Humidity-Sensor",
            "labels": [
                "humidity",
                "sensors",
                "rest",
                "json",
            ],
            "description": "Sensor for generating humidity values from soil.",
            "deviceResources": [
                {
                    "name": "Humidity",
                    "description": "Generates humidity readings from sensor",
                    "properties": {
                        "valueType": "Float32",
                        "readWrite": "R",
                        "defaultValue": "50",
                        "units": "%",   
                        "minimum": 0,
                        "maximum": 100
                    }
                }
            ]
        }
    }
}

const loadActuatorProfile = () => { 
    return {
        "requestId": v4(),
        "apiVersion": "v3",
        "profile": {
            "name": "Water-Pump",
            "labels": [
                "irrigation",
                "actuators",
                "rest",
                "json",
            ],
            "description": "Water pump for irrigating plants.",
            "deviceResources": [
                {
                    "name": "PumpState",
                    "description": "Current state of the pump. True - Working. False - Not working",
                    "properties": {
                        "valueType": "Bool",
                        "readWrite": "RW",
                        "defaultValue": "false",
                    }
                }
            ],
            "deviceCommands": [
                {
                    "name": "ChangePumpState",
                    "description": "Change to desired pump state.",
                    "readWrite": "RW",
                    "resourceOperations": [
                        { "deviceResource": "PumpState" }

                    ]
                }
            ]
        }
    }
}