import validator from '../utils/user-validator.js';
import * as firebaseAuth from 'firebase/auth';
import * as firestore from 'firebase/firestore';
import { firebaseInstance } from '../firebase-config.js';


const auth = firebaseAuth.getAuth(firebaseInstance);
const db = firestore.getFirestore(firebaseInstance);
const usersRef = firestore.collection(db, "users");
const userCodeRef = firestore.collection(db, "userCode");

const login = async (email, password) => { 
    try { 
        validator.loginValidate(email, password);

    }
    catch(validateError) { 
        throw { 
            code: 400,
            message: 'Validation failed.',
            details: validateError
        };
    }
    // login on firebase
    let result = await firebaseAuth.signInWithEmailAndPassword(auth, email, password);
    // let jwt = result.user.getIdToken();
    // fetch user name from firestore
    let query = firestore
    .query(
        usersRef, 
        firestore.where('authId', "==", result.user.uid)
    );
    let querySnapshot = await firestore.getDocs(query);
    let user = querySnapshot.docs[0].data();
    return user;
}

const register = async (user) => { 
    try { 
        validator.registerValidate(user);

    }
    catch(validateError) { 
        throw { 
            code: 400,
            message: 'Validation failed.',
            details: validateError
        };
    }
    
    let result = await firebaseAuth.createUserWithEmailAndPassword(auth, user.email, user.password);
    let userDb = { 
        authId: result.user.uid,
        email: user.email,
        name: user.name,
        mqttToken: result.user.email
    }
    await firestore.addDoc(usersRef, userDb)
    return userDb;
}

const fetchMqttToken = async (email) => { 
    // fetch token from firebase
    let query = firestore
    .query(
        usersRef, 
        firestore.where('email', "==", email)
    );
    let querySnapshot = await firestore.getDocs(query);
    let user = querySnapshot.docs[0].data();
    return user.mqttToken;
}



export default { 
    register,
    login,
    fetchMqttToken
}