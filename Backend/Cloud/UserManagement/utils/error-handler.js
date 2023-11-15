import responseDtoMapper from "./response-dto-mapper.js";

export const handleApiError = (res, error) => { 
    console.log(error);
    if (error.code == "ECONNREFUSED") { 
        let errorDto = responseDtoMapper.errorToResponseDto(
            500,
            "Connection failure",
            "User Management couldn't connect to required service."
        );
        res.status(errorDto.status).send(errorDto);
        return;
    }
    if (isJsonParsable(error)) { 
        let parsedError = JSON.parse(error);
        let errorDto = responseDtoMapper.errorToResponseDto(
            parsedError.status ?? 500,
            parsedError.message,
            parsedError.details 
        )
        res.status(errorDto.status).send(errorDto);
    }
    else { 
        let errorDto = responseDtoMapper.errorToResponseDto(
            error.status,
            error.message,
            error.details ?? error
        )
        res.status(errorDto.status).send(errorDto);
    }
} 

const isJsonParsable = (obj) => { 
    try { 
        JSON.parse(obj);
    }
    catch(error) { 
        
        return false
    }
    return true;
}