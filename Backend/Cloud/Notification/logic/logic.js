import { Notification } from "../models/notification-model.js";
import { notificationMapper } from "../utils/dto-mapper.js";

const addNotification = async (notification) => { 
    try { 
        let result = await Notification.create(notification);
        return notificationMapper.toNotificationDto(result);
    }
    catch(error) { 
        throw { 
            status: 500,
            message: error
        };
    }
}

const getAll = async (userId) => { 
    try { 
        let result = await Notification.find({userId: userId});
        return notificationMapper.toNotificationsDto(result);
    }
    catch(error) { 
        throw { 
            status: 400,
            message: error
        };
    }
}

const markRead = async (id) => { 
    try {
        await Notification.findById(id).updateOne({isUnread: false});
    }
    catch(error) { 
        throw { 
            status: 500,
            message: error
        }
    }
}

const removeNotification = async (id) => { 
    try {
        await Notification.findByIdAndDelete(id);
    }
    catch(error) { 
        throw { 
            status: 500,
            message: error
        }
    }
}

const hasUnreadNotifications = async (id) => { 
    try {
        let unreadNotifications = await Notification.exists({isRead: false, userId: id});
        return unreadNotifications != null;
    }
    catch(error) { 
        throw { 
            status: 500,
            message: error
        }
    }
}

export default { 
    addNotification,
    getAll,
    markRead,
    removeNotification,
    hasUnreadNotifications
}

