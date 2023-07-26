const { userDAO, todoDAO } = require("../DAO")

class ToDoBUS {
    createToDo = async (username, todoObj) => {
        const user = await userDAO.getUser(username)

        const newToDo = await todoDAO.createToDo(user._id, todoObj)
        return newToDo
    }

    updateTodo = async (username, todoId, fieldName, fieldValue) => {
        const user = await userDAO.getUser(username)

        const updatedToDo = await todoDAO.updateToDo(user._id, todoId, fieldName, fieldValue)


        if (!updatedToDo) {
            throw new Error("ToDo not found or does not belong to the user")
        }

        return updatedToDo
    }

    deleteToDo = async (username, todoId) => {
        const user = await userDAO.getUser(username)

        const deletedToDo = await todoDAO.deleteToDo(user._id, todoId)

        if (!deletedToDo) {
            throw new Error("ToDo not found or does not belong to the user")
        }

        return deletedToDo
    }

    getAllToDo = async (username) => {
        const user = await userDAO.getUser(username)

        const todoList = await todoDAO.getAllToDo(user._id)

        return todoList
    }
}

module.exports = new ToDoBUS