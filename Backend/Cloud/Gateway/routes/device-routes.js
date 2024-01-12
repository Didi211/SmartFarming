import Router from 'express';
const router = Router();
import deviceController from '../controllers/device-controller.js'
import ruleController from '../controllers/rule-controller.js';
import headerMiddleware from '../middlewares/check-headers.js';

const middlewares = [headerMiddleware.checkEmail, headerMiddleware.checkUserId]
// calling all this api from frontend
// devices
router.get('/user/:userId', deviceController.getAll);
router.get('/:id', deviceController.getById);
router.post('/', middlewares, deviceController.add);
router.put('/:id', headerMiddleware.checkEmail ,deviceController.update);
router.delete('/:id', headerMiddleware.checkEmail, deviceController.remove);

// calling this from edge
router.put('/actuator/:id/state', deviceController.updateState);

// rules
router.get('/:id/rule', ruleController.getByDeviceId);
router.get('/rule/user/:id', ruleController.getByUserId);
router.post('/rule', headerMiddleware.checkEmail, ruleController.add)
router.put('/:id/rule', headerMiddleware.checkEmail, ruleController.update);
router.delete('/:id/rule',headerMiddleware.checkEmail, ruleController.remove);

export default router