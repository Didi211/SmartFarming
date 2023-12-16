import { Notification } from "../models/notification-model.js";
import { notificationMapper } from "../utils/dto-mapper.js";

const addNotification = async (notification) => { 
    try { 
        let existing = await Notification
       .findOne({
            deviceId: notification.deviceId,
            // deviceStatus: notification.deviceStatus
        }).sort({
            updatedAt: -1
        });
        console.log('Existing notif:', existing);
        console.log('New notif:', notification);
        if (existing) { 
            if (existing.deviceStatus === notification.deviceStatus) { 
                let updatedNotif = await Notification.findByIdAndUpdate(existing.id, notification, { new: true});
                console.log('Updated existing notification.');
                return notificationMapper.toNotificationDto(updatedNotif);
            }
        }
        let result = await Notification.create(notification);
        console.log('Added new notification.');
        return notificationMapper.toNotificationDto(result);
    }
    catch(error) { 
        throw { 
            status: 500,
            message: 'MongoDB error',
            details: error
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
            message: 'MongoDB error',
            details: error
        };
    }
}

const markRead = async (id) => { 
    try {
        let result = await Notification.findByIdAndUpdate(id, { isRead: true});
        if (!result) { 
            throw { 
                status: 400,
                message: 'MongoDB error',
                details: `Notification with ID [${id}] not found in database.`
            }
        }
    }
    catch(error) { 
        throw { 
            status: error.status ?? 500,
            message: error.message,
            details: error.details ?? error
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
            message: 'MongoDB error',
            details: error
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
            message: 'MongoDB error',
            details: error
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

