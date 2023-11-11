import { MongoClient, ServerApiVersion } from "mongodb";




const config = async () => { 
    const uri = process.env.MONGO_DB_URI
    const client = new MongoClient(uri, { 
        serverApi: { 
            version: ServerApiVersion.v1,
            strict: true,
            deprecationErrors: true,
        }
    });
    try { 
        await client.connect();
        await client.db("admin").command({ ping: 1 });
        console.log('Connected to MongoDB');
    }
    finally { 
        await client.close();
    }
}

export default { config }