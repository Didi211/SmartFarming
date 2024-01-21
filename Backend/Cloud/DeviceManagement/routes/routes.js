import Router from 'express';
const router = Router();
import deviceController from '../controllers/device-controller.js'
import ruleController from '../controllers/rule-controller.js';

// devices
router.get('/user/:userId', deviceController.getAll);
router.get('/:id', deviceController.getById);
router.post('/', deviceController.add);
router.put('/:id', deviceController.update);
router.put('/:id/state', deviceController.updateState)
router.delete('/:id', deviceController.remove);
router.get('/user/:userId/type/:type/available', deviceController.getAvailableDevices);

// rules
router.get('/rule/:id', ruleController.getById)
router.get('/:id/rule', ruleController.getByDeviceId);
router.get('/rule/user/:id', ruleController.getByUserId)
router.post('/rule', ruleController.add)
router.put('/rule/:id', ruleController.update);
router.delete('/rule/:id',ruleController.remove);


export default router