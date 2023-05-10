import { ObjectId } from "mongodb";
import mongoose from "mongoose";

const Schema = mongoose.Schema;

let Radionica = new Schema({
    _id:{
        type: ObjectId
    },
    organizator:{
        type: String
    },
    naziv:{
        type: String
    },
    datum:{
        type: Date
    },
    mesto:{
        type: String
    },
    kratak_opis:{
        type: String
    },
    duzi_opis:{
        type: String
    },
    glavna_slika:{
        type: String
    },
    slike:{
        type: Array<String>
    },
    preostalo_mesta:{
        type: Number
    },
    rezervisani:{
        type: Array<String>
    },
    naCekanju:{
        type: Array<String>
    },
    broj_komentara:{
        type: Number
    },
    broj_lajkova:{
        type: Number
    },
    status:{
        type: String
    }, 
})

export default mongoose.model("Radionica", Radionica, 'radionice');