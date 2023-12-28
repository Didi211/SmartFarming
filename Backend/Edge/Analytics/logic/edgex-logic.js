import { edgexMetadataAxios, edgexRulesEngineAxios } from "../config/axios-config.js"
import { regular as constants } from "../utils/constants.js"
import { getDeviceConfig } from "../utils/device-config-factory.js";
import KuiperRuleBuilder from "../utils/kuiper-rule-builder.js";

const addDevice = async (device) => { 
    let operatingState = device.status === constants.ONLINE ? constants.STATE_UP : constants.STATE_DOWN;
    let deviceConfig = getDeviceConfig(device.type);
    let data = [
        {
          "apiVersion": "v3",
          "device": {
            "name": device.name,
            "description": device.description ?? null,
            "adminState": "UNLOCKED",
            "operatingState": operatingState,
            "labels": [
                device.type
            ],
            "serviceName": "device-rest",
            "profileName": deviceConfig.profileName,
            "protocols": {
              "REST": { 
                "Host": deviceConfig.host,
                "Port": deviceConfig.port,
                "Path": `${deviceConfig.path}/${device.name}`
              }
            },
            "tags": { 
              "mongoId": device.id
            }
          }
        }
    ]
    try {
        let response = await edgexMetadataAxios.post('/device',JSON.stringify(data));
        if (response.status != 207) { 
            throw error
        }
        return (JSON.parse(response.data))[0].id; // device ID in edgex 
    } catch (error) {
        throw error;
    }
    
}

const updateDevice = async (id, device) => { 
    let data = [
        {
          "apiVersion": "v3",
          "device": {
            "id": id,
            "name": device.name,
            "description": device.description ?? null,
          }
        }
    ]
    try {
        let response = await edgexMetadataAxios.patch('/device',JSON.stringify(data));
        if (response.status != 207) { 
            throw error
        }
    } catch (error) {
        throw error;
    }
}

const removeDevice = async (name) => { 
    try {
        let response = await edgexMetadataAxios.delete(`/device/name/${name}`);
        if (response.status != 200) { 
            throw error
        }
    } catch (error) {
        throw error;
    }
}



const createStream = async(name) => { 
  let result = await edgexRulesEngineAxios.post('/streams', JSON.stringify({
    sql: `create stream ${name} () WITH (FORMAT=\"JSON\", TYPE=\"edgex\")`
  }));
  if (result.status != 201) { 
    throw result.data
  }
}

const getStream = async (name) => { 
  let response = await edgexRulesEngineAxios.get(`/streams`);
  if (response.status != 200) { 
    throw response.data;
  }
  let streams = JSON.parse(response.data);
  return streams.find(stream => stream === name)
}

const addRule = async (rule, actuatorName, sensorName) => {
  let coreCommandUrl = `${process.env.EDGEX_CORE_COMMAND_URL}/device/name`;
  let startRule = KuiperRuleBuilder
    .addId(`${rule.id}-START`)
    .addSqlString(
      process.env.KUIPER_STREAM_NAME,
      rule.startExpression, 
      rule.startTriggerLevel,
      sensorName)
    .addAction('mqtt', {
      'server': process.env.EDGE_MQTT_URL,
      'topic': process.env.KUIPER_TOPIC,
      'qos': 1,
    })
    .addAction('rest', { 
      'url': `${coreCommandUrl}/${actuatorName}/PumpState?state=true`,
      "method": "put",
      "dataTemplate": `{"name":"${actuatorName}", "state":"true"}`
    })
    .build();

  let stopRule = KuiperRuleBuilder
    .addId(`${rule.id}-STOP`)
    .addSqlString(
      process.env.KUIPER_STREAM_NAME, 
      rule.stopExpression,
      rule.stopTriggerLevel,
      sensorName)
    .addAction('mqtt', {
      'server': process.env.EDGE_MQTT_URL,
      'topic': process.env.KUIPER_TOPIC,
      'qos': 1,
    })
    .addAction('rest', { 
      'url': `${coreCommandUrl}/${actuatorName}/PumpState?state=false`,
      "method": "put",
      "dataTemplate": `{"name":"${actuatorName}", "state":"false"}`
    })
    .build();

  let result = await Promise.all([
    edgexRulesEngineAxios.post('/rules', JSON.stringify(startRule)),
    edgexRulesEngineAxios.post('/rules', JSON.stringify(stopRule))
  ])
  result.forEach(response => {
    console.log(response.data, response.status);
    if (response.status !== 201) { 
      throw `Error while creating rule in Kuiper. Error: ${response.data}`
    }
  });
}


const updateRule = async (rule, sensorName) => { 
  // rule names cannot be changed
  let [startRuleResponse, stopRuleResponse] = await Promise.all([
    edgexRulesEngineAxios.get(`/rules/${rule.id}-START`),
    edgexRulesEngineAxios.get(`/rules/${rule.id}-STOP`)
  ]);
  if (startRuleResponse.status !== 200) { 
    throw `Error while udating START rule in Kuiper. Error: ${startRuleResponse.data}`
  }
  if (stopRuleResponse.status !== 200) { 
    throw `Error while udating STOP rule in Kuiper. Error: ${stopRuleResponse.data}`
  }

  let startRule = JSON.parse(startRuleResponse.data);
  let stopRule = JSON.parse(stopRuleResponse.data);

  let stream = process.env.KUIPER_STREAM_NAME;
  
  let newStartRule = KuiperRuleBuilder
    .addId(startRule.id)
    .addSqlString(
      stream, 
      rule.startExpression, 
      rule.startTriggerLevel,
      sensorName);
  startRule.actions.forEach(action => { 
    let actionType = Object.keys(action)[0];
    let actionConfig = action[actionType];
    newStartRule.addAction(actionType, actionConfig);
  })
  newStartRule = newStartRule.build();


  let newStopRule = KuiperRuleBuilder
    .addId(stopRule.id)
    .addSqlString(
      stream,
      rule.stopExpression, 
      rule.stopTriggerLevel,
      sensorName);
  stopRule.actions.forEach(action => { 
    let actionType = Object.keys(action)[0];
    let actionConfig = action[actionType];
    newStopRule.addAction(actionType, actionConfig);
  });
  newStopRule = newStopRule.build();

  let result = await Promise.all([
    edgexRulesEngineAxios.put(`/rules/${startRule.id}`, JSON.stringify(newStartRule)),
    edgexRulesEngineAxios.put(`/rules/${stopRule.id}`, JSON.stringify(newStopRule))
  ])
  result.forEach(response => {
    console.log(response.data, response.status);
    if (response.status !== 200) { 
      throw `Error while updating rule in Kuiper. Error: ${response.data}`
    }
  });
} 

const removeRule = async (id) => { 
  let result = await Promise.all([
    edgexRulesEngineAxios.delete(`/rules/${id}-START`),
    edgexRulesEngineAxios.delete(`/rules/${id}-STOP`)
  ])
  result.forEach(response => {
    console.log(response.data, response.status);
    if (response.status !== 200) { 
      throw `Error while updating rule in Kuiper. Error: ${response.data}`
    }
  });
  
}


export default { 
    addDevice,
    updateDevice,
    removeDevice,

    createStream,
    getStream,
    addRule,
    updateRule,
    removeRule
}