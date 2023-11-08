import Router from 'express';
const router = Router();
import controller from '../controllers/notification-controller.js';

// calling this api from edge
router.post('/', controller.add);

// calling this api from frontend
router.get('/',controller.getAll);
router.put('/:id', controller.markRead);
router.delete('/:id', controller.remove);