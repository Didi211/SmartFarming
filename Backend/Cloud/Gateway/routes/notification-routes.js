import Router from 'express';
const router = Router();
import notificationController from '../controllers/notification-controller.js';

router.post('/', notificationController.add);
router.get('/',notificationController.getAll);
router.put('/:id', notificationController.markRead);
router.delete('/:id', notificationController.remove);