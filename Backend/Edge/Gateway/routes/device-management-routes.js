import Router from 'express';
const router = Router();
import controller from '../controllers/device-management-controller.js';

router.post('/alert', controller.alert);

export default router
