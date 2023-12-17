import { SensorModel } from "../models/sensor-model.js";

const addSensor = async (req, res) => { 
    try { 
        let device = req.body;
        SensorModel.create({
            _id: device.id,
            sensorName: device.sensorName
        }).save();
        let message = `Added sensor ${device.sensorName}`
        console.log(message);
        res.status(200).send(message);
    }
    catch(error) { 
        console.log(error);
        res.status(500).send(error.toString());
    }
}

const addActuator = async (req, res) => { 
    try { 
        let device = req.body;
        SensorModel.update({_id: device.id}, {
            actuatorName: device.actuatorName,
            pumpState: device.pumpState
        }).save();
        let message = `Added actuator ${device.actuatorName}`
        console.log(message);
        res.status(200).send(message);
    }
    catch(error) { 
        console.log(error);
        res.status(500).send(error.toString());
    }
}

const updateSensor = async (req, res) => { 
    try { 
        let id = req.params.id;
        let device = req.body;
        SensorModel.update({_id: id}, {
            sensorName: device.sensorName 
        }).save();
        let message = `Updated sensor ${device.sensorName}`
        console.log(message);
        res.status(200).send(message);

    }
    catch(error) { 
        console.log(error);
        res.status(500).send(error.toString());
    }
}

const updateActuatorName = async (req, res) => { 
    try { 
        let oldActuatorName = req.body.oldActuatorName;
        let newActuatorName = req.body.newActuatorName;
        SensorModel.update({actuatorName: oldActuatorName}, {
            actuatorName: newActuatorName 
        }).save();
        let message = `Updated actuator name to ${newActuatorName}`
        console.log(message);
        res.status(200).send(message);

    }
    catch(error) { 
        console.log(error);
        res.status(500).send(error.toString());
    }
}


const setPumpState = async (req, res) => { 
  try {
      let name = req.params.name;
      let state = req.body.state; 
      let result = SensorModel.update({actuatorName: name}, { pumpState: state}).save();
      res.status(200).send(result);
  } catch (error) {
    console.log(error);
    res.status(500).send(error.toString());
  }
}


const removeSensor = async (req, res) => { 
    try { 
        let name = req.params.name;
        SensorModel.remove({ sensorName: name });
        let message = `Removed sensor ${name}`
        console.log(message);
        res.status(200).send(message);
    }
    catch(error) { 
        console.log(error);
        res.status(500).send(error.toString());
    }
}

const removeActuator = async (req, res) => { 
    try { 
        let name = req.params.name;
        let result = SensorModel.find({ actuatorName: name });
        if (result.length > 0) {
            result.update({
                actuatorName: null
            }).save();
        }
        else { 
            throw "Actuator not found"
        }
        let message = `Removed actuator ${name}`
        console.log(message);
        res.status(200).send(message);
    }
    catch(error) { 
        console.log(error);
        res.status(500).send(error.toString());
    }
}

export default { 
    addSensor,
    updateSensor,
    updateActuatorName,
    removeSensor,
    addActuator,
    setPumpState,
    removeActuator
}