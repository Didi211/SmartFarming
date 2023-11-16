import logic from '../logic/rule-logic.js';
import { handleApiError } from '../utils/error-handler.js'

const getByDeviceId = async (req, res) => { 
    // get rule for specific device
    try { 
        let result = await logic.getRuleFromDeviceId(req.params.id);
        res.status(result.status).send(result);
    }
    catch(error) { 
        handleApiError(res, error);
    }

}

const add = async (req, res) => { 
    // add rule
    try { 
        let result = await logic.add(req.body);
        console.log(result);
        res.status(result.status).send(result);
    }
    catch(error) { 
        handleApiError(res, error);
    }
}

const update = async (req, res) => { 
    try { 
        let result = await logic.update(req.params.id, req.body);
        res.status(result.status).send(result);
    }
    catch(error) { 
        handleApiError(res, error);
    }
}

const remove = async (req, res) => { 
    try { 
        let result = await logic.remove(req.params.id);
        res.status(result.status).send(result);
    }
    catch(error) { 
        handleApiError(res, error);
    }
}

export default { 
    getByDeviceId,
    add,
    update,
    remove
}