import responseDtoMapper from "./response-dto-mapper.js";

export const handleApiError = (res, error) => { 
    console.log(error);
    if (error.code == "ECONNREFUSED") { 
        let errorDto = responseDtoMapper.errorToResponseDto(
            500,
            "Connection failure",
            "Notification couldn't connect to required service."
        );
        res.status(errorDto.status).send(errorDto);
        return;
    }
    if (isJsonParsable(error)) { 
        let parsedError = JSON.parse(error);
        let errorDto = responseDtoMapper.errorToResponseDto(
            parsedError.status ?? 500,
            parsedError.message,
            parsedError.details?.toString() 
        )
        res.status(errorDto.status).send(errorDto);
    }
    else { 
        let errorDto = responseDtoMapper.errorToResponseDto(
            error.status,
            error.message,
            error.details?.toString() ?? error
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