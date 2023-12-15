import { SensorModel } from "../models/sensor-model.js";

const add = async (req, res) => { 
    try { 
        let data = req.body;
        SensorModel.create({_id: data.name, pumpState: data.pumpState}).save();
        console.log(`Added sensor ${data.name}`);
        res.status(200).send('Added')
    }
    catch(error) { 
        console.log(error);
        res.status(500).send(error);
    }
}

const remove = async (req, res) => { 
    try { 
        let name = req.params.name;
        SensorModel.remove({ _id: name })
        console.log(`Removed sensor ${name}`);
        res.status(204).send();
    }
    catch(error) { 
        console.log(error);
        res.status(500).send(error);
    }
}

const setPumpState = async (req, res) => { 
    let name = req.params.name;
    let state = req.body.state;
    try { 
        SensorModel.update({_id: name}, { pumpState: state}).save();
        res.status(204).send();
    }
    catch(error) { 
        console.log(error);
        res.status(500).send(error);
    }
    
}


export default { 
    add, 
    remove,
    setPumpState,
}