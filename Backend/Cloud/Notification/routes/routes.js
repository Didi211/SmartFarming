import Router from 'express';
const router = Router();
import controller from '../controllers/controller.js';

router.post('/', controller.add);
router.get('/user/:id', controller.getAll);
router.put('/:id/markRead', controller.markRead);
router.delete('/:id', controller.remove);
router.get('/user/:id/unread', controller.hasUnread)

export default router