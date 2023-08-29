const { genAcessToken } = require("../tokenHandler")
const { userBUS } = require("../BUS")

class UserController {
    login = async (req, res) => {
        try {
            const { username, password } = req.body
            const userPayload = await userBUS.login(username, password)
            
            const token = genAcessToken(userPayload)
            res.cookie('jwt', token)
            res.status(200).json(userPayload)
        } catch (error) {
            console.log(error)
            res.status(500).json(error)
        }
    }

    register = async (req, res) => {
        try {
            const {username, password} = req.body
            const userPayload = await userBUS.register(username, password)

            const token = genAcessToken(userPayload)
            res.cookie('jwt', token)
            res.status(200).json(userPayload)
        } catch (error) {
            console.log(error)
            res.status(500).json(error)
        }
    }
}

module.exports = new UserController()