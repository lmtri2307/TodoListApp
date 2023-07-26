const { userModel } = require("../models")

class UserDAO {
    getUser = async (username) => {
        const user = await userModel.findOne({
            username
        }).exec()
        if (!user) {
            throw new Error("Username don't exist")
        }

        return user.toObject()
    }

    createUser = async (username, password) => {
        const newUser = await userModel.create({
            username, password
        })
        return newUser.toObject()
    }
}

module.exports = new UserDAO()