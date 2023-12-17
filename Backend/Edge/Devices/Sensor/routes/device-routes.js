import Router from 'express';
const router = Router();
import controller from '../controllers/device-controller.js';

router.post('/add/sensor', controller.addSensor);
router.post('/add/actuator', controller.addActuator);
router.put('/update/sensor/:id', controller.updateSensor);
router.put('/update/actuator/pump-state/:name', controller.setPumpState);
router.delete('/remove/sensor/:name', controller.removeSensor);
router.delete('/remove/actuator/:name', controller.removeActuator);

export default router
