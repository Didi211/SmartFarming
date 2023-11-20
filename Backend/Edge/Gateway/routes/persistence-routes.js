import Router from 'express';
const router = Router();
import controller from '../controllers/persistence-controller.js';

router.post('/sync', controller.saveData);

export default router
