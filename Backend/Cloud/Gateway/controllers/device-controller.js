import logic from '../logic/device-logic.js';
import { handleApiError } from '../utils/error-handler.js';

const getAll = async (req, res) => {
    let type = req.query.type;
    let userId = req.params.userId;
    try { 
        let result = await logic.getAllDevices(userId, type);
        res.status(result.status).send(result);
    }
    catch(error) { 
        handleApiError(res, error);
    }
}

const getById = async (req, res) => {
    try { 
        let result = await logic.get(req.params.id);
        res.status(result.status).send(result);
    }
    catch(error) { 
        handleApiError(res, error);

    }
}

const add = async (req, res) => {
    try { 
        let result = await logic.add(req.body)
        res.status(result.status).send(result);
    }
    catch(error) { 
        handleApiError(res, error);

    }
}

const update = async (req, res) => {
    try { 
        let result = await logic.update(req.params.id, req.body)
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
    getAll,
    getById,
    add,
    update,
    remove
}