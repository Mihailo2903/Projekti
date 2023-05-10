import mongoose from "mongoose";

const Schema = mongoose.Schema;

let Korisnik = new Schema({
    ime:{
        type: String
    },
    prezime:{
        type: String
    },
    korisnicko_ime:{
        type: String
    },
    tip:{
        type: String
    },
    lozinka:{
        type: String
    },
    telefon:{
        type: Number
    },
    mejl:{
        type: String
    },
    naziv_organizacije:{
        type: String
    },
    drzava:{
        type: String
    },
    grad:{
        type: String
    },
    postanski_broj:{
        type: Number
    },
    ulica:{
        type: String
    },
    broj:{
        type: Number
    },
    maticni_broj:{
        type: Number
    },
    status:{
        type: String
    },
    privremena_lozinka:{
        type: String
    },
    trenutak_zahteva:{
        type: Date
    },
    slika:{
        type:String
    }
})

export default mongoose.model("Korisnik", Korisnik, 'korisnici');