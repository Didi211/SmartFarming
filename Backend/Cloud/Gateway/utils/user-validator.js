const loginValidate = (username, password) => { 
    let result = "";
    try { 
        if (username == undefined || username == "") { 
            throw "Username is required.";
        }
        if (password == undefined || password == "") { 
            throw "Password is required.";
        }
    }
    catch(error) { 
        result = error;
    }
    return result;
}

const registerValidate = (user) => { 
    let result = "";
    try { 
        result = loginValidate(user.username, user.password);
        if (result != "") { 
            throw result;
        }
        if (user.email == undefined || user.email == "") { 
            throw "Email is required.";
        }
        // email regex validation
    }
    catch(error) { 
        result = error;
    }
    return result;
}
export default { 
    loginValidate,
    registerValidate
}