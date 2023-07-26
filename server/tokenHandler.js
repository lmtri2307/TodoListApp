const crypto = require('crypto');
const secret = crypto.randomBytes(64).toString('hex');
const jwt = require('jsonwebtoken')

const toClient = ['username', "_id"]
const genAcessToken = (user) => {
    const payload = toClient.reduce((acc, key) => {
        acc[key] = user[key];
        return acc;
    }, {});
    const token = jwt.sign(payload, secret, { expiresIn: "30m" })
    return token
}


const verify = (token) => {
    const payload = jwt.verify(token, secret)
    return payload
}

const getPayload = (token) => {
    return jwt.decode(token)
}

const verifyUser = (req, res, next) => {
    const token = req.cookies.jwt
    if (!token) {
        res.status(403).send("Token is missing")
        return
    }

    try {
        const userPayload = verify(token)
        req.user = userPayload
    } catch (error) {
        console.log(error)
        res.status(403).json(error)
        return
    }
    next()
}

module.exports = {
    genAcessToken,
    verifyUser,
}