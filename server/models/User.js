const { Schema, default: mongoose } = require("mongoose");


const UserSchema = new Schema({
    username: {
        type: String,
        required: true,
        min: 3,
        max: 20,
        unique: true,
    },
    password: {
        type: String,
        required: true,
        min: 6,
    }
});

module.exports = mongoose.model("User", UserSchema);
