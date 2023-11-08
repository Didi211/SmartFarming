import service from "../services/notification-service.js";

const add = async (notification) => { 
    await service.saveNotification(notification);
    // push notification to the android app
}

const getAll = async (userId) => { 
    return await service.getAllNotifications(userId);
}

const markRead = async (id) => {
    await service.markRead(id);
}

const remove = async (id) => {
    await service.removeNotification(id);
}


export default { 
    add,
    getAll,
    markRead,
    remove
}