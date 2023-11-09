

const loginValidate = (email, password) => { 
    validateString(email, "Email");
    validateString(password, "Password");
}

const registerValidate = (user) => { 
    loginValidate(user.email, user.password);
    validateString(user.name, "Name");
    validatePasswords(user.password, user.confirmPassword);
}

const validateString = (value, name) => { 
    if (value == undefined || value == "") { 
        throw `${name} is required.`;
    }
}

const validatePasswords = (password, confirmPassword) => { 
    validateString(password, "Password");
    validateString(confirmPassword, "Confirm password");
    if (password != confirmPassword) { 
        throw "Passwords do not match.";
    }
}
export default { 
    loginValidate,
    registerValidate
}