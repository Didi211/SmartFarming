import validator from '../utils/user-validator.js';
import * as firebaseAuth from 'firebase/auth';
import * as firestore from 'firebase/firestore';
import { firebaseInstance } from '../firebase-config.js';
import { toUserDto } from '../utils/dto-mapper.js';
import { uuid } from 'uuidv4';

const auth = firebaseAuth.getAuth(firebaseInstance);
const db = firestore.getFirestore(firebaseInstance);
const usersRef = firestore.collection(db, "users");

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
    let id = querySnapshot.docs[0].id;
    let user = querySnapshot.docs[0].data();
    return toUserDto(id, user);
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
    let userData = { 
        authId: result.user.uid,
        email: user.email,
        name: user.name,
        mqttToken: uuid()
    }
    let id = (await firestore.addDoc(usersRef, userData)).id;
    return toUserDto(id, userData);
}

const fetchMqttToken = async (email) => { 
    // fetch token from firebase
    let query = firestore
    .query(
        usersRef, 
        firestore.where('email', "==", email)
    );
    let querySnapshot = await firestore.getDocs(query);
    if (querySnapshot.docs.length > 0) { 
        let user = querySnapshot.docs[0].data();
        return user.mqttToken;
    }
    throw { 
        status: 400,
        message: `User with email [${email}] not found.`
    }
}

const isUserExisting = async (userId) => {
    let docRef = firestore.doc(db, usersRef.id, userId);
    let docSnap = await firestore.getDoc(docRef);
    return docSnap.exists();
}

export default { 
    register,
    login,
    fetchMqttToken,
    isUserExisting
}