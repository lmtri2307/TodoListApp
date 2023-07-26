var express = require('express');
const { todoController } = require('../controllers');

var router = express.Router();


// Create new todo
router.post('/', todoController.createToDo);

router.put('/:todoId/task', todoController.updateTask);

router.put('/:todoId/status', todoController.updateStatus);

router.delete("/", todoController.deleteToDo)


router.get("/", todoController.getAllTodo)


module.exports = router;
