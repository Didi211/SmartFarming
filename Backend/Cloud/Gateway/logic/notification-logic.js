import { notificationsAxios, usersAxios } from "../axios-config.js";

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
    await isUserExisting(userId);
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
    await isUserExisting(userId);
    let response = await notificationsAxios.get(`/user/${userId}/has-unread`);
    if (response.status == 200) { 
        console.log(response.data);
        return JSON.parse(response.data);
    }
    else { 
        throw response.data;
    }
}

const isUserExisting = async (userId) => { 
    let userExistsResult = await usersAxios.get(`/${userId}/exists`);
    let userExists = (JSON.parse(userExistsResult.data)).details; // returns boolean as string - must parse
    if (!userExists) {  // JS sees "false" as true - string value with length > 0 is true
        throw { 
            status: 400,
            message: 'Query error',
            details: `User with id [${userId}] not found in database.`
        }
    }
}


export default { 
    add,
    getAll,
    markRead,
    remove,
    hasUnread
}