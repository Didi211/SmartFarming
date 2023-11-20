import Router from 'express';
const router = Router();
import controller from '../controllers/controller.js';

router.post('/login', controller.login);
router.post('/register', controller.register);
router.post('/fetch-token', controller.fetchMqttToken);
router.get('/:id/exists', controller.isUserExisting);
router.get('/get-id/:token', controller.getIdByMqttToken);

export default router