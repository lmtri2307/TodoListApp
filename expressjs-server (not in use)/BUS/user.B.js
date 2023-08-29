
const { userDAO } = require("../DAO");
const bcrypt = require('bcrypt');
const saltRounds = 10;


class UserBUS {
    checkPassword = async (password, hashedPassword) => {
        return (await bcrypt.compare(password, hashedPassword))
    }

    login = async (username, password) => {
        const user = await userDAO.getUser(username)

        const checkPass = await this.checkPassword(password, user.password)

        if(!checkPass){
            throw new Error("Wrong password")
        }

        return user
    }

    register = async (username, password) => {
        try {
            const hashedPassword = await bcrypt.hash(password, saltRounds)

            const newUser = await userDAO.createUser(username, hashedPassword)

            return newUser
            
        } catch (error) {
            if (error.name === "MongoServerError" && [11000, 11001].includes(error.code)) {
                const violatedField = Object.keys(error.keyPattern)[0];
                error = {
                    message : `${violatedField} is already used by someone`
                }
            }
            throw error
        }
    }
}

module.exports = new UserBUS()