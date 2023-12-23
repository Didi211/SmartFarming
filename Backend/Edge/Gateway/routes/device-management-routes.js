import Router from 'express';
const router = Router();
import controller from '../controllers/device-management-controller.js';

router.post('/alert', controller.alert);
router.put('/actuator/update/state', controller.updateState);

export default router
