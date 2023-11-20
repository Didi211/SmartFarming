
import { usersAxios } from "../config/axios-config.js";

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

export default { isUserExisting }