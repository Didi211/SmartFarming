import Router from 'express';
const router = Router();
import controller from '../controllers/sensor-data-controller.js';

// calling this api from edge
router.post('/sync', controller.saveData);

router.get('/data-hours',controller.getHourlyHistory);
router.get('/data-months',controller.getMonthlyHistory);
router.get('/data-years',controller.getYearlyHistory);

export default router;