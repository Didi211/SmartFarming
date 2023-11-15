const errorToResponseDto = (status, message, details) => { 
    return { 
        status: status ?? 500, 
        message: message ?? "Error occured.", 
        details: details ?? null
    }
}

const succesfullResponseDto = (status, message, details) => { 
    return { 
        status: status ?? 200,
        message: message ?? "Action successful.",
        details: details ?? null
    }
}

export default { 
    errorToResponseDto,
    succesfullResponseDto
}