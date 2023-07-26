const { todoModel } = require("../models")

class ToDoDAO {
    createToDo = async (userId, todoObj) => {
        const newToDo = await todoModel.create({
            user: userId,
            ...todoObj
        })

        return newToDo.toObject()
    }

    updateToDo = async (userId, todoId, fieldName, fieldValue) => {

        const updatedToDo = await todoModel.findOneAndUpdate({
            _id: todoId,
            user: userId
        }, {
            [fieldName]: fieldValue
        }, {
            returnDocument: "after"
        })

        return updatedToDo ? updatedToDo.toObject() : null
    }

    deleteToDo = async (userId, todoId) => {
        const deletedToDo = await todoModel.findOneAndDelete(
            { _id: todoId, user: userId },
            { returnDocument: "before" }
        )

        return deletedToDo ? deletedToDo.toObject() : null
    }

    getAllToDo = async (userId) => {
        const todoList = await todoModel.find({
            user: userId
        }).exec()

        return todoList.map((item) => item.toObject())
    }
}

module.exports = new ToDoDAO()