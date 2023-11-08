import logic from '../logic/rule-logic.js';

const getByDeviceId = async (req, res) => { 
    // get rule for specific device
    try { 
        let rule = await logic.getRuleFromDeviceId(req.params.id);
        res.status(200).send(rule);
    }
    catch(error) { 
        res.status(500).send(error);
    }

}

const add = async (req, res) => { 
    // add rule
    try { 
        let rule = await logic.add(req.body);
        res.status(200).send(rule);
    }
    catch(error) { 
        res.status(500).send(error);
    }
}

const update = async (req, res) => { 
    try { 
        let rule = await logic.update(req.params.id, req.body);
        res.status(200).send(rule);
    }
    catch(error) { 
        res.status(500).send(error);
    }
}

const remove = async (req, res) => { 
    try { 
        await logic.remove(req.params.id);
        res.status(204).send();
    }
    catch(error) { 
        res.status(500).send(error);
    }
}

export default { 
    getByDeviceId,
    add,
    update,
    remove
}