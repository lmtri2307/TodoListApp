const { Schema, default: mongoose } = require("mongoose");


const ToDoSchema = new Schema({
    user: {
        type: Schema.Types.ObjectId,
        required: true,
        ref: 'User'
    },
    task: {
        type: String,
        required: true
    },
    status: {
        type: Boolean,
        default: false
    }
    
});

module.exports = mongoose.model("ToDo", ToDoSchema);
