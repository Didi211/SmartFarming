export const handleApiError = (res, error) => { 
    try { 
        let parsedError = JSON.parse(error); 
        res.status(parsedError.status || 500).send(parsedError);
    }
    catch(err) { 
        console.log(err);
        res.status(500).send(error);
    }
} 