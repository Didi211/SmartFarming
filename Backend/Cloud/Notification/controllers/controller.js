import logic from '../logic/logic.js';
import { handleApiError } from '../utils/error-handler.js';
import responseDtoMapper from '../utils/response-dto-mapper.js';

const add = async (req, res) => { 
    let notification = req.body;
    try { 
        let result = await logic.addNotification(notification);
        let responseDto = responseDtoMapper.succesfullResponseDto(
            200,
            "Creating notification successfull.",
            result
        )
        res.status(responseDto.status).send(responseDto);
    }
    catch(error) { 
        handleApiError(res, error);
    }
}

const getAll = async (req, res) => { 
    let userId = req.params.id;
    try { 
        let result = await logic.getAll(userId);
        if (result.length > 0) { 
            let responseDto = responseDtoMapper.succesfullResponseDto(
                200,
                "Notifications fetched",
                result
            )
            res.status(responseDto.status).send(responseDto);
        }
        else { 
            let responseDto = responseDtoMapper.succesfullResponseDto(
                200, // 204 has no response data
                "No existing notifications.",
                result
            )
            res.status(responseDto.status).send(responseDto);
        }
    }
    catch(error) { 
        handleApiError(res, error);
    }
}

const markRead = async (req, res) => { 
let id = req.params.id;
    try { 
        await logic.markRead(id);
        let responseDto = responseDtoMapper.succesfullResponseDto(
            200,
            "Updated notification",
            result
        )
        res.status(responseDto.status).send(responseDto);
    }
    catch(error) { 
        handleApiError(res, error);
    }
}

const remove = async (req, res) => { 
    let id = req.params.id;
    try { 
        await logic.removeNotification(id);
        let responseDto = responseDtoMapper.succesfullResponseDto(
            200,
            "Removed notification",
            result
        )
        res.status(responseDto.status).send(responseDto);
    }
    catch(error) { 
        handleApiError(res, error);
    }
}
const hasUnread = async (req, res) => { 
    let userId = req.params.id;
    try { 
        let result = await logic.hasUnreadNotifications(userId);
        let responseDto = responseDtoMapper.succesfullResponseDto(
            200,
            result ? 'Has new notifications' : 'No new notifications',
            result
        )
        res.status(responseDto.status).send(responseDto);
        
    }
    catch(error) { 
        handleApiError(res, error);
    }   
}
export default { 
    add, 
    getAll,
    markRead,
    remove,
    hasUnread
}