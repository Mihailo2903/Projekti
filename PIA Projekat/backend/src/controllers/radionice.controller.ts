import * as express from 'express';
import radionica from '../models/radionica';
import korisnik from '../models/korisnik';
import ucesce from '../models/ucesce';
import { ObjectId } from 'mongodb';
import aktivnost from '../models/aktivnost';
import poruka from '../models/poruka';


const nodemailer = require('nodemailer');

export class RadionicaController{

    dohvatiRadionice= (req:express.Request, res:express.Response)=>{
        radionica.find({"status":"odobrena"},(err,korisnik)=>{
            if(err) console.log(err);
            else res.json(korisnik);
        })
    }

    dohvatiNeodobreneRadionice = (req:express.Request, res:express.Response)=>{
        radionica.find({"status":"neodobrena"},(err,korisnik)=>{
            if(err) console.log(err);
            else res.json(korisnik);
        })
    }

    dohvatiRadionicu= (req:express.Request, res:express.Response)=>{
        let idRad = req.body.idRad;

        radionica.findOne({"_id": new ObjectId(idRad)},(err,rad)=>{
            if(err) console.log(err);
            else res.json(rad);
        })
    }

    dohvatiRadionicePoNazivu= (req:express.Request, res:express.Response)=>{
        let naziv = req.body.naziv;
        radionica.find({"naziv": {$regex: naziv},"status":"odobrena"},(err,rad)=>{
            if(err) console.log(err);
            else res.json(rad);
        })
    }

    dohvatiRadionicePoMestu= (req:express.Request, res:express.Response)=>{
        let mesto = req.body.mesto;

        radionica.find({"mesto": {$regex: mesto},"status":"odobrena"},(err,rad)=>{
            if(err) console.log(err);
            else res.json(rad);
        })
    }

    dohvatiRadionicePoNazivuIMestu= (req:express.Request, res:express.Response)=>{
        let naziv = req.body.naziv;
        let mesto = req.body.mesto;

        radionica.find({"naziv": {$regex: naziv}, "mesto": {$regex: mesto}, "status":"odobrena"},(err,rad)=>{
            if(err) console.log(err);
            else res.json(rad);
        })
    }

    dohvatiTop5 = (req:express.Request, res:express.Response)=>{
        radionica.find({"status":"odobrena"}).sort({ broj_lajkova: -1 }).limit(5).find((err, result) => {
          //  console.log(result);
            res.json(result);
          });
    }

    dohvatiSvaUcesca = (req:express.Request, res:express.Response)=>{
        ucesce.find({},(err,korisnik)=>{
            if(err) console.log(err);
            else res.json(korisnik);
        })
    }

    dohvatiIstoriju = (req:express.Request, res:express.Response)=>{
        let korisnik = req.body.korisnik;

        ucesce.find({"korisnik": korisnik},(err,rad)=>{
            if(err) console.log(err);
            else res.json(rad);
        })
    }

    dohvatiMojeRadionice = (req:express.Request, res:express.Response)=>{
        let radionice = req.body.radionice;
        let IDS=[];
        radionice.forEach(element => {
            IDS.push(new ObjectId(element));
        });

        radionica.find({"_id": {$in: IDS}},(err,rad)=>{
            if(err) console.log(err); 
            else res.json(rad);
        })
    }

    dohvatiRezervisaneRadionice = (req:express.Request, res:express.Response)=>{
        let korisnik = req.body.korisnik;

        radionica.find({"rezervisani": korisnik},(err,rad)=>{
            if(err) console.log(err); 
            else res.json(rad);
        })
    }

    dohvatiRadioniceNaCekanju = (req:express.Request, res:express.Response)=>{
        let korisnik = req.body.korisnik;

        radionica.find({"naCekanju": korisnik},(err,rad)=>{
            if(err) console.log(err); 
            else res.json(rad);
        })
    }

    dohvatiAktivnosti = (req:express.Request, res:express.Response)=>{
        let korisnik = req.body.korisnik;

        aktivnost.find({"korisnik": korisnik},(err,rad)=>{
            if(err) console.log(err); 
            else res.json(rad);
        })
    }

    obrisiAktivnost = (req:express.Request, res:express.Response)=>{
        let idAk = req.body.idAk;
        let tip = req.body.tip;
        let idRad = req.body.idRad;

        aktivnost.findOneAndDelete({"_id": new ObjectId(idAk)},(err,rad)=>{
            if(err) console.log(err); 
            else {
                if(tip == "lajk"){
                    radionica.findOneAndUpdate({"_id": new ObjectId(idRad)},{$inc:{"broj_lajkova": -1}},(err1,rad1)=>{
                        if(err1) console.log(err1); 
                        else res.json(rad1);
                    })
                } else{
                    radionica.findOneAndUpdate({"_id": new ObjectId(idRad)},{$inc:{"broj_komentara": -1}},(err1,rad1)=>{
                        if(err1) console.log(err1); 
                        else res.json(rad1);
                    })
                }
            }
        })
    }

    azurirajAktivnost = (req:express.Request, res:express.Response)=>{
        let idAk = req.body.idAk;
        let novi = req.body.novi;

        aktivnost.findOneAndUpdate({"_id": new ObjectId(idAk)},{$set:{"sadrzaj":novi}},(err,rad)=>{
            if(err) console.log(err); 
            else res.json(rad);
        })
    }

    dohvatiPoruke = (req:express.Request, res:express.Response)=>{
        let idRad = req.body.idRad;
        let korisnik = req.body.korisnik;

        poruka.find({"radionica": idRad, "korisnik": korisnik},(err,rad)=>{
            if(err) console.log(err); 
            else res.json(rad);
        })
    }

    posaljiPoruku = (req:express.Request, res:express.Response)=>{
        let por = new poruka({
            radionica: req.body.radionica,
            korisnik: req.body.korisnik,
            sadrzaj: req.body.sadrzaj,
            datum: new Date(),
            tip: req.body.tip
        })

        por.save().then(por=>{
            res.status(200).json({"poruka":"OK"});
        })
    }
    
    otkaziUcesceNaRadionici = (req:express.Request, res:express.Response)=>{
        let idRad = req.body.idRad;
        let korisnik = req.body.korisnik;

        ucesce.findOneAndDelete({"radionica": idRad, "korisnik":korisnik},(err,rad)=>{
            if(err) console.log(err);       
            radionica.findOneAndUpdate({"_id": new ObjectId(idRad)},{$inc:{"preostalo_mesta": 1}, $set:{"naCekanju":[]}},(err1,rad1)=>{
                if(err1) console.log(err1); 
                else res.json(rad1);
            })   
        })
    }

    proveriUcesceNaRadionici = (req:express.Request, res:express.Response)=>{
        let idRad = req.body.idRad;
        let korisnik = req.body.korisnik;

        ucesce.findOne({"radionica": idRad, "korisnik":korisnik},(err,rad)=>{
            if(err) console.log(err);       
            res.json(rad);
        })
    }

    rezervisiMestoNaRadionici = (req:express.Request, res:express.Response)=>{
        let idRad = req.body.idRad;
        let korisnik = req.body.korisnik;

        radionica.findOneAndUpdate({"_id": new ObjectId(idRad)},{$push:{"rezervisani": korisnik}, $inc:{"preostalo_mesta": -1}},(err1,rad1)=>{
            if(err1) console.log(err1); 
            else res.json(rad1);
        })
    }

    dodajNaListuZaCekanje = (req:express.Request, res:express.Response)=>{
        let idRad = req.body.idRad;
        let mejl = req.body.mejl;

        radionica.findOneAndUpdate({"_id": new ObjectId(idRad)},{$push:{"naCekanju": mejl}},(err1,rad1)=>{
            if(err1) console.log(err1); 
            else res.json(rad1);
        })
    }

    dohvatiRadioniceSaIstimNazivom = (req:express.Request, res:express.Response)=>{
        let radionica = req.body.radionica;
        let korisnik = req.body.korisnik;

        ucesce.find({"naziv": radionica, "korisnik": korisnik},(err1,rad1)=>{
            if(err1) console.log(err1); 
            else res.json(rad1);
        })
    }

    dohvatiAktivnostiRadionice = (req:express.Request, res:express.Response)=>{
        let idRad = req.body.idRad;

        aktivnost.find({"radionica": idRad},(err,rad)=>{
            if(err) console.log(err); 
            else res.json(rad);
        })
    }

    dodajAktivnost = (req:express.Request, res:express.Response)=>{
        let ak = new aktivnost({
            _id: new ObjectId(),
            radionica: req.body.radionica,
            naziv: req.body.naziv,
            korisnik: req.body.korisnik,
            tip: req.body.tip,
            sadrzaj: req.body.sadrzaj,
            datum: new Date()
        })

        ak.save().then(akt=>{
            if(req.body.tip == "lajk"){
                radionica.findOneAndUpdate({"_id": new ObjectId(req.body.radionica)},{$inc:{"broj_lajkova": 1}},(err1,rad1)=>{
                    if(err1) console.log(err1); 
                    else res.json(rad1);
                })
            } else{
                radionica.findOneAndUpdate({"_id": new ObjectId(req.body.radionica)},{$inc:{"broj_komentara": 1}},(err1,rad1)=>{
                    if(err1) console.log(err1); 
                    else res.json(rad1);
                })
            }
        }).catch(err=>{
            res.status(400).json({"poruka":"GRESKA"})
            console.log(err);
        })
    }

    dodajRadionicu = (req:express.Request, res:express.Response)=>{
        let rad = new radionica({
            _id: new ObjectId(),
            organizator: req.body.organizator,
            naziv: req.body.naziv,
            datum: new Date(req.body.datum),
            mesto: req.body.mesto,
            kratak_opis: req.body.kratak_opis,
            duzi_opis: req.body.duzi_opis,
            glavna_slika: req.body.glavna_slika,
            slike: req.body.slike,
            preostalo_mesta: req.body.preostalo_mesta,
            rezervisani: [],
            naCekanju: [],
            broj_komentara: 0,
            broj_lajkova: 0,
            status: req.body.status
        })

        rad.save().then(akt=>{
            res.status(200).json({"poruka":"OK"});
        }).catch(err=>{
            res.status(400).json({"poruka":"GRESKA"})
            console.log(err);
        })
    }

    proveriPostojanjePredlogaRadionice = (req:express.Request, res:express.Response)=>{
        let korisnik = req.body.korisnik;

        radionica.findOne({"organizator": korisnik, "status":"neodobrena"},(err,rad)=>{
            if(err) console.log(err);       
            res.json(rad);
        })
    }

    azurirajRadionicu = (req:express.Request, res:express.Response)=>{
        let id= req.body.id;
        let naziv= req.body.naziv;
        let datum= new Date(req.body.datum);
        let mesto= req.body.mesto;
        let kratak_opis= req.body.kratak_opis;
        let duzi_opis= req.body.duzi_opis;
        let glavna_slika= req.body.glavna_slika;
        let slike= req.body.slike;

        radionica.findOneAndUpdate({"_id": new ObjectId(id)},{$set:{"naziv":naziv, "datum":datum, "mesto":mesto,
            "kratak_opis":kratak_opis, "duzi_opis":duzi_opis, "glavna_slika":glavna_slika, "slike":slike}}, (err,ra)=>{
            if(err) console.log(err);
            res.json(ra);
        })
    }

    dohvatiUcescaNaRadionici = (req:express.Request, res:express.Response)=>{
        let id= req.body.id;

        ucesce.find({"radionica": id}, (err,uc)=>{
            if(err) console.log(err);
            res.json(uc);
        })
    }

    prihvatiRezervaciju = (req:express.Request, res:express.Response)=>{
        let id = req.body.id;
        let naziv = req.body.naziv;
        let korisnik = req.body.korisnik;

        let u = new ucesce({
            radionica: id,
            naziv: naziv,
            korisnik: korisnik
        });

        radionica.findOneAndUpdate({"_id": new ObjectId(id)}, {$pull: {"rezervisani": korisnik}} ,(err,uc)=>{
            u.save().then(akt=>{
                res.status(200).json(akt);
            })
        })
    }

    odbijRezervaciju = (req:express.Request, res:express.Response)=>{
        let id = req.body.id;
        let korisnik = req.body.korisnik;

        radionica.findOneAndUpdate({"_id": new ObjectId(id)}, {$pull: {"rezervisani": korisnik}, $set: {"naCekanju": []}, $inc:{
            "preostalo_mesta": 1}} ,(err,uc)=>{
            if(err) console.log(err);
            res.json(uc);
        })
    }

    otkaziRadionicu = (req:express.Request, res:express.Response)=>{
        let id = req.body.id;
        
        radionica.findOneAndUpdate({"_id": new ObjectId(id)}, { $set: {"status": "otkazana"}} ,(err,uc)=>{
            if(err) console.log(err);
            ucesce.deleteMany({"radionica":id},(err,u)=>{
                if(err) res.json(err);
                res.json(u);
            })
        })
    }

    azurirajNaziveRadionice = (req:express.Request, res:express.Response)=>{
        let id = req.body.id;
        let novi = req.body.novi;
        
        aktivnost.updateMany({"radionica": id},{$set:{"naziv":novi}}, (err,r)=>{
            if(err) console.log(err);
            ucesce.updateMany({"radionica": id},{$set:{"naziv":novi}}, (err1,r1)=>{
                if(err1) console.log(err1);  
            })
        })
    }
    
    dohvatiRadioniceOrganizatora = (req:express.Request, res:express.Response)=>{
        let korisnik= req.body.korisnik;

        radionica.find({"organizator": korisnik}, (err,uc)=>{
            if(err) console.log(err);
            res.json(uc);
        })
    }

    obrisiRadionicu = (req:express.Request, res:express.Response)=>{
        let id = req.body.id;

        radionica.deleteOne({"_id": new ObjectId(id)},(err1,r1)=>{
            if(err1) console.log(err1);
            aktivnost.deleteMany({"radionica": id}, (err2,r2)=>{
                if(err2) console.log(err2);
                poruka.deleteMany({"radionica": id}, (err3,r3)=>{
                    if(err3) console.log(err3);
                    ucesce.deleteMany({"radionica": id}, (err4,r4)=>{
                        if(err4) console.log(err4);
                        res.json(r4);
                    })
                })
            })
            
        })
    }

    postaviStatusRadionice = (req:express.Request, res:express.Response)=>{
        let id = req.body.id;
        let status = req.body.status;

        radionica.findOneAndUpdate({"_id": new ObjectId(id)},{$set:{"status": status}},(err1,r1)=>{
            if(err1) console.log(err1);
            res.json(r1);
        })
    }


    azurirajAktivnostiRadionicePosleBrisanjaKorisnika = (req:express.Request, res:express.Response)=>{
        let id = req.body.id;
        let broj_lajkova = -1 * parseInt(req.body.broj_lajkova);
        let broj_komentara = -1 * parseInt(req.body.broj_komentara);

        radionica.findOneAndUpdate({"_id": new ObjectId(id)},{$inc:{"broj_lajkova": broj_lajkova, "broj_komentara": broj_komentara}},(err1,r1)=>{
            if(err1) console.log(err1);
            res.json(r1);
        })
    }

    otkaziPrijavuZaRadionicu = (req:express.Request, res:express.Response)=>{
        let id = req.body.id;
        let mejl = req.body.mejl;

        radionica.findOneAndUpdate({"_id": new ObjectId(id)}, {$pull: {"naCekanju": mejl}} ,(err,uc)=>{
            if(err) console.log(err);
            res.json(uc);
        })
    }
}