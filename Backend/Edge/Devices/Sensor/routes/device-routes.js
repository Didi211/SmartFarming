import Router from 'express';
const router = Router();
import controller from '../controllers/device-controller.js';

router.post('/add', controller.add);
router.delete('/remove/:name', controller.remove);
router.put('/update/:name/state/', controller.setPumpState);

export default router
