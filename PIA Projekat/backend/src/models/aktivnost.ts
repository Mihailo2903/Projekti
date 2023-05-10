import { ObjectId } from "mongodb";
import mongoose from "mongoose";

const Schema = mongoose.Schema;

let Aktivnost = new Schema({
    _id:{
        type: ObjectId
    },
    radionica:{
        type: String
    },
    naziv:{
        type: String
    },
    korisnik:{
        type: String
    },
    tip:{
        type: String
    },
    sadrzaj:{
        type: String
    },
    datum:{
        type: Date
    }
})

export default mongoose.model("Aktivnost", Aktivnost, 'aktivnosti');