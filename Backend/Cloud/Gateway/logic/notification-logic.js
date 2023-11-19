import { notificationsAxios } from "../config/axios-config.js";
import userValidator from "../utils/user-validator.js";

const add = async (notification) => { 
    let response = await notificationsAxios.post('/', JSON.stringify(notification));
    if (response.status == 200) { 
        // push notification to the android app
        return JSON.parse(response.data);
    }
    else { 
        throw response.data;
    }
}

const getAll = async (userId) => { 
    await userValidator.isUserExisting(userId);
    let response = await notificationsAxios.get(`/user/${userId}`);
    if (response.status == 200 || response.status == 204 ) { 
        return JSON.parse(response.data);
    }
    else { 
        throw response.data;
    }
}

const markRead = async (id) => {
    let response = await notificationsAxios.put(`/${id}/mark-read`);
    if (response.status == 200) { 
        return JSON.parse(response.data);
    }
    else { 
        throw response.data;
    }
}

const remove = async (id) => {
    let response = await notificationsAxios.delete(`/${id}`);
    if (response.status == 200) { 
        return JSON.parse(response.data);
    }
    else { 
        throw response.data;
    }
}

const hasUnread = async (userId) => { 
    await userValidator.isUserExisting(userId);
    let response = await notificationsAxios.get(`/user/${userId}/has-unread`);
    if (response.status == 200) { 
        return JSON.parse(response.data);
    }
    else { 
        throw response.data;
    }
}



export default { 
    add,
    getAll,
    markRead,
    remove,
    hasUnread
}