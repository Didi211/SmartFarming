import Router from 'express';
const router = Router();
import controller from '../controllers/device-controller.js';

router.post('/', controller.add);
router.put('/:id', controller.update);
router.delete('/:id', controller.remove);
router.put('/:id/state', controller.changeState);
router.put('/:id/status', controller.changeStatus);

export default router
