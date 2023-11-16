import Router from 'express';
const router = Router();
import controller from '../controllers/controller.js';

// api called from edge
router.post('/sync', controller.syncData);

// api called from frontend
router.post('/:id/hourly', controller.getHourlyData);
// router.post('/:id/monthly', controller.getMonthlyData);
// router.post('/:id/yearly', controller.getYearlyData);
// router.post('/avg-hourly',controller.averageDataHourly);
// router.post('/avg-monthly',controller.averageDatMonthly);
// router.post('/avg-yearly',controller.averageDataYearly);

export default router;



