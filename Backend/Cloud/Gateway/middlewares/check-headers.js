import constants from "../utils/constants.js";
import { handleApiError } from "../utils/error-handler.js";

const checkEmail = (req, res, next) => { 
    try { 
        if (!req.headers[constants.emailHeader]) { 
            throw {
                status: 400,
                message: 'Missing header',
                details: `Required header: [${constants.emailHeader}]`
            }
        }
        next();
    }
    catch(error) { 
        handleApiError(res, error);
    }
}

const checkUserId = (req, res, next) => { 
    try { 
        if (!req.headers[constants.userIdHeader]) { 
            throw {
                status: 400,
                message: 'Missing header',
                details: `Required header: [${constants.userIdHeader}]`
            }
        }
        next();
    }
    catch(error) { 
        handleApiError(res, error);
    }
}

export default { 
    checkEmail,
    checkUserId
}