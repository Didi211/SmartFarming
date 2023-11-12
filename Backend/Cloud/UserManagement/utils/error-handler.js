export const handleApiError = (res, error) => { 
    if (error.code == "ECONNREFUSED") { 
        res.status(500).send("Gateway couldn't connect to required service.");
        return;
    }
    try { 
        let parsedError = JSON.parse(error); 
        res.status(parsedError.status || 500).send(parsedError);
    }
    catch(err) { 
        console.log(error);
        res.status(500).send(error);
    }
} 