export const mqttTopicBuilder = (startsWith, ...args) => { 
    return `${startsWith}/${args.join('/')}`;
}