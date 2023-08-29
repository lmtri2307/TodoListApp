const { todoBUS } = require('../BUS');

class ToDoController {
    createToDo = async (req, res) => {
        try {
            const { username } = req.user
            const newTodoObj = req.body

            const newToDo = await todoBUS.createToDo(username, newTodoObj)

            res.status(200).json(newToDo)
        } catch (error) {
            console.log(error)
            res.status(500).json(error)
        }
    }

    updateTask = async (req, res) => {
        try {
            const { username } = req.user
            const { todoId } = req.params
            const { task } = req.body

            const updatedToDo = await todoBUS.updateTodo(username, todoId, "task", task)

            res.status(200).json(updatedToDo)
        } catch (error) {
            console.log(error)
            res.status(500).json(error)
        }
    }

    updateStatus = async (req, res) => {
        try {
            const { username } = req.user
            const { todoId } = req.params
            const { status } = req.body

            const updatedToDo = await todoBUS.updateTodo(username, todoId, "status", status)
            res.status(200).json(updatedToDo)

        } catch (error) {
            console.log(error)
            res.status(500).json(error)
        }
    }

    deleteToDo = async (req, res) => {
        try {
            const { username } = req.user
            const { todoId } = req.body

            const deletedToDo = await todoBUS.deleteToDo(username, todoId)

            res.status(200).send("Deleted")
        } catch (error) {
            console.log(error)
            res.status(500).json(error)
        }
    }

    getAllTodo = async (req, res) => {
        try {
            const { username } = req.user

            const todoList = await todoBUS.getAllToDo(username)

            res.status(200).json(todoList)
        } catch (error) {
            console.log(error)
            res.status(500).json(error)
        }
    }
}

module.exports = new ToDoController()