// Import the mongoose module
const mongoose = require("mongoose");

const connect = (mongoDB) => {
    return mongoose.connect(mongoDB)
} 

const connectDB = async () => {
    mongoose.set('strictQuery', false);

    const mongoDB = process.env.MONGODB_URL;
    
    // Wait for database to connect, logging an error if there is a problem 
    try {
        await connect(mongoDB)
        console.log("Connect database")
    } catch (error) {
        console.log(error)
    }
}

module.exports = connectDB