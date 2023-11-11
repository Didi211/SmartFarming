import mongoose from "mongoose";

const config = async () => { 
    const uri = process.env.MONGO_DB_URI
    await mongoose.connect(uri);
    console.log('Connected to MongoDB.');
}

export default { config }