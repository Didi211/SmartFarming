
const saveNotification = async (alert) => { return true } 

const getAllNotifications = async (userId) => { return [] }

const removeNotification = async (id) => { return true }

const markRead = async (id) => { return true }

export default { 
    saveNotification,
    getAllNotifications,
    removeNotification,
    markRead
}