import { ObjectId } from "mongodb";
import mongoose from "mongoose";

const Schema = mongoose.Schema;

let Ucesce = new Schema({
    radionica:{
        type: String
    },
    naziv:{
        type: String
    },
    korisnik:{
        type: String
    }
})

export default mongoose.model("Ucesce", Ucesce, 'ucesca');