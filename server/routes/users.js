var express = require('express');
const User = require('../models/User');
var router = express.Router();
const bcrypt = require('bcrypt');
const { genAcessToken } = require('../tokenHandler');
const saltRounds = 10;

/* GET users listing. */
router.post('/login', async function (req, res, next) {
  try {
    const user = await User.findOne({
      username: req.body.username
    }).exec()
    if(!user)
      throw new Error("Username don't exist")
    const checkPass = await bcrypt.compare(req.body.password, user.password)
    console.log("checkPass", checkPass)
    if (checkPass)
    {
      const token = genAcessToken(user)
      res.cookie('jwt', token)
      res.status(200).json(user)
    }
    else
      throw new Error("Wrong password")
  } catch (error) {
    console.log(error)
    res.status(500).send(error.message)
  }
});

router.post('/register', async function (req, res, next) {
  try {

    const newUser = await User.create({
      username: req.body.username,
      password: await bcrypt.hash(req.body.password, saltRounds)
    })
    const token = genAcessToken(newUser)
    res.cookie('jwt', token)
    res.status(200).json(newUser.toJSON())
  } catch (error) {
    console.log(error)
    res.status(500).json(error)
  }
});


module.exports = router;
