import logic from "../logic/notification-logic.js"
import { handleApiError } from "../utils/error-handler.js";

const add = async (req, res) => { 
    let notification = req.body;
    try { 
        await logic.add(notification);
        res.status(204).send();
    }
    catch(error) { 
        handleApiError(res, error);
    }
}

const getAll = async (req, res) => { 
    let userId = req.params.id;
    try { 
        let notifications = await logic.getAll(userId);
        if (notifications.length > 0) { 
            res.status(200).send(notifications);
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
        await logic.remove(id);
        res.status(204).send();
    }
    catch(error) { 
        handleApiError(res, error);
    }
}

const hasUnread = async (req, res) => { 
    let userId = req.params.id;
    try { 
        let result = await logic.hasUnread(userId);
        console.log("result",result)
        if (result.status == 200) { 
            res.status(200).send(result);
        }
        else if (result.status == 400) { 
            res.status(400).send(result);
        }
    }
    catch(error) { 
        console.log("wrong user throw", error)
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