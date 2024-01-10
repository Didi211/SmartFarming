const toUserDto = (id, user) => { 
    return { 
        id: id,
        authId: user.authId,
        email: user.email,
        name: user.name,
        mqttToken: user.mqttToken
    }
}

export default { 
    toUserDto,
}