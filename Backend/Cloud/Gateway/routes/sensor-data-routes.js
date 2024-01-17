import Router from 'express';
const router = Router();
import controller from '../controllers/sensor-data-controller.js';
import middleware from '../middlewares/check-headers.js';
// calling this api from edge
router.post('/sync', middleware.checkMqttToken, controller.saveData);

router.get('/:id',middleware.checkUserId, controller.getHistoryData);  // /id?period=HOURS || MONTHS || YEARS

export default router;