import mongoose from "mongoose";

const config = async () => { 
    const uri = process.env.MONGO_DB_URI
    try { 
        await mongoose.connect(uri);
        console.log('Connected to MongoDB.');
    }
    catch(error) {
        console.log(error);
    }
}

export default { config }