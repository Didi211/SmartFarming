import Router from 'express';
const router = Router();
import deviceController from '../controllers/device-controller.js'
import ruleController from '../controllers/rule-controller.js';


// calling all this api from frontend
// devices
router.get('/', deviceController.getAll);
router.get('/:id', deviceController.getById);
router.post('/', deviceController.add);
router.put('/:id', deviceController.update);
router.delete('/:id', deviceController.remove);

// rules
router.get('/:id/rule', ruleController.getByDeviceId);
router.post('/:id/rule', ruleController.add)
router.put('/:id/rule', ruleController.update);
router.delete('/:id/rule',ruleController.remove);

export default router