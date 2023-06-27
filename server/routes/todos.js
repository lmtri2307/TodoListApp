var express = require('express');
const ToDo = require('../models/ToDo');
const User = require('../models/User');
const { getPayload } = require('../tokenHandler');
var router = express.Router();


// Create new todo
router.post('/', async function (req, res, next) {
    try {
        const username = getPayload(req.cookies.jwt).username
        const user = await User.findOne({
            username : username
        }).exec()
        const newToDo = await ToDo.create({
            user: user._id,
            ...req.body
        })
        res.status(200).json(newToDo)
    } catch (error) {
        console.log(error)
        res.status(500).json(error)
    }
});

router.put('/:todoId/task', async function (req, res, next) {
    try {
        const username = getPayload(req.cookies.jwt).username
        const user = await User.findOne({
            username : username
        }).exec()
        await ToDo.updateOne({
            _id: req.params.todoId,
            user: user._id
        }, {
            task: req.body.task
        }).then(result => res.status(200).json(result))
    } catch (error) {
        console.log(error)
        res.status(500).json(error)
    }
});

router.put('/:todoId/status', async function (req, res, next) {
    try {
        const username = getPayload(req.cookies.jwt).username
        const user = await User.findOne({
            username : username
        }).exec()
        await ToDo.updateOne({
            _id: req.params.todoId,
            user: user._id
        }, {
            status: req.body.status
        }).then(result => res.status(200).json(result))
    } catch (error) {
        console.log(error)
        res.status(500).json(error)
    }
});

router.delete("/", async function(req, res ,next){
    try {
        const username = getPayload(req.cookies.jwt).username
        const user = await User.findOne({
            username : username
        }).exec()
        await ToDo.deleteOne({
            _id: req.body.todoId,
            user: user._id
        })
        res.status(200).send("Deleted")
    } catch (error) {
        console.log(error)
        res.status(500).json(error)
    }
})


router.get("/", async function (req, res, next) {
    try {
        const username = getPayload(req.cookies.jwt).username
        console.log("username:",username)
        const user = await User.findOne({
            username: username
        }).exec()
        console.log(user)
        const todo = await ToDo.find({
            user: user._id
        }).exec()
        if (!todo)
            throw new Error("Todo don't exist")
        res.status(200).json(todo)
    } catch (error) {
        console.log(error)
        res.status(500).json(error)
    }
})


module.exports = router;
