import logic from '../logic/device-logic.js';

const getAll = async (req, res) => {
    let filter = req.query.type;
    try { 
        let devices = await logic.getAllDevices(filter);
        if (devices.length > 0) { 
            res.status(200).send(devices);
        }
        else { 
            res.status(204).send();
        }
    }
    catch(error) { 
        res.status(500).send(error);
    }
}

const getById = async (req, res) => {
    try { 
        let device = await logic.get(req.params.id);
        res.status(200).send(device);
    }
    catch(error) { 
        res.status(500).send(error);
    }
}

const add = async (req, res) => {
    try { 
        let result = await logic.add(req.body)
        res.status(200).send(result)
    }
    catch(error) { 
        res.status(error.code || 500).send(error);
    }
}

const update = async (req, res) => {
    try { 
        let result = await logic.update(req.params.id, req.body)
        res.status(200).send(result);
    }
    catch(error) { 
        res.status(error.code || 500).send(error);
    }
    
}

const remove = async (req, res) => {
    try { 
        await logic.remove(req.params.id);
        res.status(204).send();
    }
    catch(error) { 
        res.status(error.code || 500).send(error);
    }

}

export default { 
    getAll,
    getById,
    add,
    update,
    remove
}