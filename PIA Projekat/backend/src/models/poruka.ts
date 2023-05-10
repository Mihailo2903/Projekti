import { ObjectId } from "mongodb";
import mongoose from "mongoose";

const Schema = mongoose.Schema;

let Poruka = new Schema({
    radionica:{
        type: String
    },
    korisnik:{
        type: String
    },
    sadrzaj:{
        type: String
    },
    datum:{
        type: Date
    },
    tip:{
        type: String
    },
})

export default mongoose.model("Poruka", Poruka, 'poruke');