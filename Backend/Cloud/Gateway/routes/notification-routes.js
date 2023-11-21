import Router from 'express';
const router = Router();
import controller from '../controllers/notification-controller.js';

// calling this api from frontend
router.get('/user/:id',controller.getAll);
router.put('/:id/mark-read', controller.markRead);
router.delete('/:id', controller.remove);
router.get('/user/:id/has-unread', controller.hasUnread);

export default router;