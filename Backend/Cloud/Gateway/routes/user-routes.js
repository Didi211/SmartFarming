import Router from 'express';
const router = Router();
import controller from '../controllers/user-controller.js';

// this api calls frontend
router.post('/register',controller.register);
router.post('/login', controller.login);

// this api calls edge for MQTT connection
router.post('/fetch-token', controller.fetchMqttToken);

router.get('/id:/exists', controller.isUserExisting)

export default router