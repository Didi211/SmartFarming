import Router from 'express';
const router = Router();
import controller from '../controllers/rule-controller.js';

router.post('/', controller.add)
router.put('/:id', controller.update);
router.delete('/:id',controller.remove);

export default router
