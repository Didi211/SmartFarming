import logic from '../logic/logic.js';
import { handleApiError } from '../utils/error-handler.js';

const add = async (req, res) => { 
    let notification = req.body;
    try { 
        await logic.addNotification(notification);
        res.status(204).send();
    }
    catch(error) { 
        handleApiError(res, error);
    }
}

const getAll = async (req, res) => { 
    let userId = req.params.id;
    try { 
        let result = await logic.getAll(userId);
        if (result.length > 0) { 
            res.status(200).send(result);
        }
        else { 
            res.status(204).send();
        }
    }
    catch(error) { 
        handleApiError(res, error);
    }
}

const markRead = async (req, res) => { 
let id = req.params.id;
    try { 
        await logic.markRead(id);
        res.status(204).send();
    }
    catch(error) { 
        handleApiError(res, error);
    }
}

const remove = async (req, res) => { 
    let id = req.params.id;
    try { 
        await logic.removeNotification(id);
        res.status(204).send();
    }
    catch(error) { 
        handleApiError(res, error);
    }
}
const hasUnread = async (req, res) => { 
    let userId = req.params.id;
    try { 
        return await logic.hasUnread(userId);
        
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