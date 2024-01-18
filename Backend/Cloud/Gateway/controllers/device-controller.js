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

const getAvailableDevices = async (req, res) => {
    try { 
        let userId = req.params.userId;
        let type = req.params.type
        let result = await logic.getAvailableDevices(userId, type);
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
        let email = req.headers['user-email'];
        let userId = req.headers['user-id'];
        let result = await logic.add(req.body, email, userId)
        res.status(result.status).send(result);
    }
    catch(error) { 
        handleApiError(res, error);

    }
}

const update = async (req, res) => {
    try { 
        let email = req.headers['user-email'];
        let result = await logic.update(req.params.id, req.body, email);
        res.status(result.status).send(result);
    }
    catch(error) { 
        handleApiError(res, error);
    }
}

const updateState = async (req, res) => {
    try { 
        let result = await logic.updateState(req.params.id, req.body.state);
        res.status(result.status).send(result);
    }
    catch(error) { 
        handleApiError(res, error);
    }
}

const remove = async (req, res) => {
    try { 
        let email = req.headers['user-email'];
        let userId = req.headers['user-id'];
        let result = await logic.remove(req.params.id, email, userId);
        res.status(result.status).send(result);
    }
    catch(error) { 
        handleApiError(res, error);

    }
}




export default { 
    getAll,
    getAvailableDevices,
    getById,
    add,
    update,
    remove,
    updateState
}