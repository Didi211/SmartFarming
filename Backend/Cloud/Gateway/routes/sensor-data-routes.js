import Router from 'express';
const router = Router();
import controller from '../controllers/sensor-data-controller.js';

// calling this api from edge
router.post('/sync', controller.saveData);

router.get('/:id',controller.getHistoryData);  // /id?period=HOURS || MONTHS || YEARS

export default router;