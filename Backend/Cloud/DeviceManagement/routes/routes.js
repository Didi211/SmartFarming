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

// rules
router.get('/:id/rule', ruleController.getByDeviceId);
router.get('/rule/user/:id', ruleController.getByUserId)
router.post('/rule', ruleController.add)
router.put('/:id/rule', ruleController.update);
router.delete('/:id/rule',ruleController.remove);


export default router