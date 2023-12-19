import edgexLogic from "../logic/edgex-logic.js";

export const createRuleStream = async () => { 
    try {
        let isCreated = await edgexLogic.getStream(process.env.KUIPER_STREAM_NAME);
        if (isCreated) { 
            console.log(`Edgex Rule Stream "${process.env.KUIPER_STREAM_NAME}" is already created.`);
            return;
        }
        await edgexLogic.createStream(process.env.KUIPER_STREAM_NAME);
        console.log(`Created Rule Stream "${process.env.KUIPER_STREAM_NAME}"`);
    } catch (error) {
        console.log('Error while creating Rule stream');
        console.log(error);
    }
}