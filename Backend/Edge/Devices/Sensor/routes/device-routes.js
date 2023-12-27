import Router from 'express';
const router = Router();
import controller from '../controllers/device-controller.js';

router.post('/add/sensor', controller.addSensor);
router.post('/add/actuator', controller.addActuator);
router.put('/update/sensor/:id', controller.updateSensor);
router.put('/update/actuator-name', controller.updateActuatorName)
router.put('/update/actuator/:name/PumpState', controller.setPumpState);
router.delete('/remove/sensor/:name', controller.removeSensor);
router.delete('/remove/actuator/:name', controller.removeActuator);
router.delete('/remove/sensor/id/:id', controller.removeSensorById);

export default router
