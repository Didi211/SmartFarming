import service from "../services/notification-service.js";

const add = async (notification) => { 
    await service.saveNotification(notification);
    // push notification to the android app
}

const getAll = async () => { 
    await service.getAllNotifications();
}

const markRead = async (id) => {
    service.markRead(id);
}

const remove = async (id) => {
    service.removeNotification(id);
}


export default { 
    add,
    getAll,
    markRead,
    remove
}