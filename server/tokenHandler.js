const crypto = require('crypto');
const secret = crypto.randomBytes(64).toString('hex');
const jwt = require('jsonwebtoken')

const toClient = ['username',"_id"]
const genAcessToken = (user) => {
    const payload = toClient.reduce((acc, key) => {
        acc[key] = user[key];
        return acc;
    }, {});
    const token = jwt.sign(payload, secret, { expiresIn: "1s" })
    return token
}


const verify = (token) => {
    const payload = jwt.verify(token, secret)
    return payload
}

const getPayload = (token) => {
    return jwt.decode(token)
}

module.exports = {
    genAcessToken,
    verify,
    getPayload,
}