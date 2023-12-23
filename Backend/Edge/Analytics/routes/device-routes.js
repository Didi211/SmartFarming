import Router from 'express';
const router = Router();
import controller from '../controllers/device-controller.js';

router.post('/', controller.add);
router.put('/:id', controller.update);
router.delete('/:id', controller.remove);
router.put('/:id/status', controller.changeStatus);
// router.put('/:id/state', controller.changeState);  // will be removed - updating from mqtt kuiper 

export default router
