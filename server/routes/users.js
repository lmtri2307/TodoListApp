var express = require('express');
const { userController } = require('../controllers');

var router = express.Router();

/* GET users listing. */
router.post('/login', userController.login);

router.post('/register', userController.register);


module.exports = router;
