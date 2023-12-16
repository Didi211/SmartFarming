import Router from 'express';
const router = Router();
import controller from '../controllers/data-controller.js';


router.put('/Humidity', controller.get);
// router.get('/Humidity', controller.set);

export default router