import logic from "../logic/notification-logic.js"
import { handleApiError } from "../utils/error-handler.js";


const getAll = async (req, res) => { 
    let userId = req.params.id;
    try { 
        let result = await logic.getAll(userId);
        res.status(result.status).send(result);
    }
    catch(error) { 
       handleApiError(res, error);
    }
}

const markRead = async (req, res) => {
    let id = req.params.id;
    try { 
        let result = await logic.markRead(id);
        res.status(result.status).send(result);
    }
    catch(error) { 
        handleApiError(res, error);
    }
}

const remove = async (req, res) => {
    let id = req.params.id;
    try { 
        let result = await logic.remove(id);
        res.status(result.status).send(result);
    }
    catch(error) { 
        handleApiError(res, error);
    }
}

const hasUnread = async (req, res) => { 
    let userId = req.params.id;
    try { 
        let result = await logic.hasUnread(userId);
        res.status(result.status).send(result);
    }
    catch(error) { 
        handleApiError(res, error);
    }
}

export default { 
    add,
    getAll,
    markRead,
    remove,
    hasUnread
}