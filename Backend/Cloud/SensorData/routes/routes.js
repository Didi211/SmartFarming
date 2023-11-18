import Router from 'express';
const router = Router();
import controller from '../controllers/controller.js';

// api called from edge
router.post('/sync', controller.syncData);

// api called from frontend
router.post('/:id', controller.getHistoryData); // /id?period=HOURS || MONTHS || YEARS

export default router;



