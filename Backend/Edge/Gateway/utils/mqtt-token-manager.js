import { fetchMqttToken } from "./mqtt-token-getter.js";


export class MqttTokenManager { 
    static #token = null;
    static async initialize(userEmail) { 
        if (!MqttTokenManager.#token) { 
           MqttTokenManager.#token = await fetchMqttToken(userEmail);
        }
    }
    static Token = () => {
        return MqttTokenManager.#token;
    };
    
}

