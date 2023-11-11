const toNotificationDto = (notification) => { 
    return { 
        id: notification._id,
        message: notification.message,
        userId: notification.userId,
        createdAt: notification.createdAt,
        isRead: notification.isRead
    }
}

 const toNotificationsDto = (notifications) => { 
    return notifications.map(x => toNotificationDto(x));
}

export const notificationMapper = { 
    toNotificationDto,
    toNotificationsDto
}