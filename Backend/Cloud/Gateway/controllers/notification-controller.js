import logic from "../logic/notification-logic.js"

const add = async (req, res) => { 
    let notification = req.body;
    try { 
        await logic.add(notification);
        res.status(204).send();
    }
    catch(error) { 
        res.status(500).send(error);
    }
}

const getAll = async (req, res) => { 
    let userId = req.body.params.userId;
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
        res.status(500).send(error);
    }
}

const markRead = async (req, res) => {
    let id = req.params;
    try { 
        await logic.markRead(id);
        res.status(204).send();
    }
    catch(error) { 
        res.status(500).send(error);
    }
}

const remove = async (req, res) => {
    let id = req.params;
    try { 
        await logic.remove(id);
        res.status(204).send();
    }
    catch(error) { 
        res.status(500).send(error);
    }
}

export default { 
    add,
    getAll,
    markRead,
    remove
}