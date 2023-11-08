import notificationLogic from "../logic/notification-logic.js"

const add = async (notification) => { 
    return true;
}

const getAll = async () => { 
    return [];
}

const markRead = async (id) => {
    return true;
}

const remove = async (id) => {
    return true;
}

export default { 
    add,
    getAll,
    markRead,
    remove
}