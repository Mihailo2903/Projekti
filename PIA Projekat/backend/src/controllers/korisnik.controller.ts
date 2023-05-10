import * as express from 'express';
import poruka from '../models/poruka';
import aktivnost from '../models/aktivnost';
import korisnik from '../models/korisnik';
import ucesce from '../models/ucesce';
import radionica from '../models/radionica';

const nodemailer = require('nodemailer');

export class KorisnikController{
    registrujUcesnika = (req:express.Request, res:express.Response)=>{
        let ucesnik = new korisnik({
            ime:req.body.ime,
            prezime:req.body.prezime,
            korisnicko_ime: req.body.korisnicko_ime,
            tip: "ucesnik",
            lozinka:req.body.lozinka,
            telefon:req.body.telefon,
            mejl:req.body.mejl,
            status: req.body.status,
            privremena_lozinka: null,
            trenutak_zahteva: null,
            slika: req.body.slika
        })

        ucesnik.save().then(ucesnik=>{
            res.status(200).json({"poruka":"OK"})
        }).catch(err=>{
            res.status(400).json({"poruka":"GRESKA"})
        })
    }

    registrujOrganizatora = (req:express.Request, res:express.Response)=>{
        let organizator = new korisnik({
            ime:req.body.ime,
            prezime:req.body.prezime,
            korisnicko_ime: req.body.korisnicko_ime,
            tip: "organizator",
            lozinka:req.body.lozinka,
            telefon:req.body.telefon,
            mejl:req.body.mejl,
            naziv_organizacije: req.body.naziv_organizacije,
            drzava: req.body.drzava,
            grad: req.body.grad,
            postanski_broj: req.body.postanski_broj,
            ulica: req.body.ulica,
            broj: req.body.broj,
            maticni_broj: req.body.maticni_broj,
            status: req.body.status,
            privremena_lozinka: null,
            trenutak_zahteva: null,
            slika: req.body.slika
        })

        organizator.save().then(organizator=>{
            res.status(200).json({"poruka":"OK"})
        }).catch(err=>{
            res.status(400).json({"poruka":"GRESKA"})
        })
    }

    proveraJedinstvenosti = (req:express.Request, res:express.Response)=>{
        let korisnicko_ime = req.body.korisnicko_ime;
        let mejl = req.body.mejl;

        korisnik.findOne({$or: [{korisnicko_ime: korisnicko_ime}, {mejl:mejl}]},(err,korisnik)=>{
            if(err) console.log(err);
            else res.json(korisnik);
        })
    }

    dohvatSveKorisnike = (req:express.Request, res:express.Response)=>{
        korisnik.find({$or: [{tip: "ucesnik"}, {tip:"organizator"}]},(err,korisnik)=>{
            if(err) console.log(err);
            else res.json(korisnik);
        })
    }

    dohvatSveUcesnike = (req:express.Request, res:express.Response)=>{
        korisnik.find({tip: "ucesnik"},(err,korisnik)=>{
            if(err) console.log(err);
            else res.json(korisnik);
        })
    }

    ulogujSe = (req:express.Request, res:express.Response)=>{
        let korisnicko_ime = req.body.korisnicko_ime;
        let lozinka = req.body.lozinka;

        korisnik.findOne({korisnicko_ime: korisnicko_ime, lozinka:lozinka, status: "aktivan"},(err,korisnik)=>{
            if(err) console.log(err);
            else res.json(korisnik);
        })
    }

    posaljiMejl = (req: express.Request, res:express.Response)=>{
        let mejl = req.body.mejl;
        let novaLozinka = req.body.novaLozinka;
        let trenutak = new Date();

         const transporter = nodemailer.createTransport({
             service: 'gmail',
             auth: {
                 user: 'micomilan29@gmail.com',
                 pass: 'dvkmolslxylsuqri'
             }
         });
 
         let sadrzaj = 'Poštovani, <br> <br> Vaša privremena lozinka je ' + novaLozinka + ' , ovde je mozete promeniti: <br> <a href="http://localhost:4200/promenaLozinke">Promeni lozinku</a> <br> Lozinku mozete promeniti koristeći privremenu lozinku u narednih 30 minuta.'
 
         const mailOptions = {
         from: 'micomilan29@gmail.com',
         to: mejl,
         subject: 'Zahtev za ponistavanje lozinke',
         html: sadrzaj
         };
 
        transporter.sendMail(mailOptions, (error, info) => {
            if (error) {
                console.log(error);
            } else {
                console.log('Email sent: ' + info.response);
                console.log(mejl);
            }
            korisnik.findOneAndUpdate({mejl:mejl},{$set:{ 
                privremena_lozinka: novaLozinka, 
                trenutak_zahteva: trenutak}}, (kor)=>{res.json({"poruka":"OK"})});
        });
    }

    posaljiObavestenjeOSlobodnomMestu = (req: express.Request, res:express.Response)=>{
        let mejl = req.body.mejl;
        let radionica = req.body.radionica;

        const transporter = nodemailer.createTransport({
             service: 'gmail',
             auth: {
                 user: 'micomilan29@gmail.com',
                 pass: 'dvkmolslxylsuqri'
             }
         });
 
         let sadrzaj = 'Poštovani, <br> <br> Obaveštavamo Vas da se otvorilo slobodno mesto za radionicu ' + radionica + '. <br>Sada ste u mogućnosti da rezervišete Vaše mesto na njoj.'
 
         const mailOptions = {
         from: 'micomilan29@gmail.com',
         to: mejl,
         subject: 'Obavestenje o oslobadjanju mesta',
         html: sadrzaj
         };
 
        transporter.sendMail(mailOptions, (error, info) => {
            if (error) {
                console.log(error);
            } else {
                console.log('Email sent: ' + info.response);
                //console.log(mejl);
            }
        });
    }

    posaljiObavestenjeOOtkazivanju = (req: express.Request, res:express.Response)=>{
        let mejl = req.body.mejl;
        let radionica = req.body.radionica;

        const transporter = nodemailer.createTransport({
             service: 'gmail',
             auth: {
                 user: 'micomilan29@gmail.com',
                 pass: 'dvkmolslxylsuqri'
             }
         });
 
         let sadrzaj = 'Poštovani, <br> <br> Obaveštavamo Vas da ja radionica pod imenom  ' + radionica + ' na koju ste se prijavili otkazana.'
 
         const mailOptions = {
         from: 'micomilan29@gmail.com',
         to: mejl,
         subject: 'Obavestenje o otkazivanju radionice',
         html: sadrzaj
         };
 
        transporter.sendMail(mailOptions, (error, info) => {
            if (error) {
                console.log(error);
            } else {
                console.log('Email sent: ' + info.response);
                //console.log(mejl);
            }
        });
    }

    dohvatiPoKorImenu = (req:express.Request, res:express.Response)=>{
        let korisnicko_ime = req.body.korisnicko_ime;

        korisnik.findOne({korisnicko_ime: korisnicko_ime},(err,korisnik)=>{
            if(err) console.log(err);
            else res.json(korisnik);
        })
    }

    dohvatiPoMejlu = (req:express.Request, res:express.Response)=>{
        let mejl = req.body.mejl;

        korisnik.findOne({mejl: mejl},(err,korisnik)=>{
            if(err) console.log(err);
            else res.json(korisnik);
        })
    }

    promeniLozinku = (req:express.Request, res:express.Response)=>{
        let mejl = req.body.mejl;
        let nova = req.body.nova;

        korisnik.findOneAndUpdate({mejl: mejl},{$set:{lozinka:nova}},(err,korisnik)=>{
            if(err) console.log(err);
            else res.json(korisnik);
            //console.log(nova);
        })
    }

    promeniPodatak = (req:express.Request, res:express.Response)=>{
        let mejl = req.body.mejl;
        let novo = req.body.novo;
        let broj = parseInt(req.body.broj);

        switch(broj){
            case 0: 
            korisnik.findOneAndUpdate({mejl: mejl},{$set:{korisnicko_ime:novo}},(err,korisnik)=>{
                if(err) console.log(err);
                else res.json(korisnik);
            });
            break;
            case 1: 
            korisnik.findOneAndUpdate({mejl: mejl},{$set:{ime:novo}},(err,korisnik)=>{
                if(err) console.log(err);
                else res.json(korisnik);
            });
            break;
            case 2: 
            korisnik.findOneAndUpdate({mejl: mejl},{$set:{prezime:novo}},(err,korisnik)=>{
                if(err) console.log(err);
                else res.json(korisnik);
            });
            break;
            case 3: 
            korisnik.findOneAndUpdate({mejl: mejl},{$set:{mejl:novo}},(err,korisnik)=>{
                if(err) console.log(err);
                else res.json(korisnik);
            });
            break;
            case 4: 
            korisnik.findOneAndUpdate({mejl: mejl},{$set:{telefon:parseInt(novo)}},(err,korisnik)=>{
                if(err) console.log(err);
                else res.json(korisnik);
            });
            break;
            case 5: 
            korisnik.findOneAndUpdate({mejl: mejl},{$set:{slika:novo}},(err,korisnik)=>{
                if(err) console.log(err);
                else res.json(korisnik);
            });
            break;
            case 6: 
            korisnik.findOneAndUpdate({mejl: mejl},{$set:{naziv_organizacije:novo}},(err,korisnik)=>{
                if(err) console.log(err);
                else res.json(korisnik);
            });
            break;
            case 7: 
            korisnik.findOneAndUpdate({mejl: mejl},{$set:{drzava:novo}},(err,korisnik)=>{
                if(err) console.log(err);
                else res.json(korisnik);
            });
            break;
            case 8: 
            korisnik.findOneAndUpdate({mejl: mejl},{$set:{grad:novo}},(err,korisnik)=>{
                if(err) console.log(err);
                else res.json(korisnik);
            });
            break;
            case 9: 
            korisnik.findOneAndUpdate({mejl: mejl},{$set:{ulica:novo}},(err,korisnik)=>{
                if(err) console.log(err);
                else res.json(korisnik);
            });
            break;
            case 10: 
            korisnik.findOneAndUpdate({mejl: mejl},{$set:{broj:parseInt(novo)}},(err,korisnik)=>{
                if(err) console.log(err);
                else res.json(korisnik);
            });
            break;
            case 11: 
            korisnik.findOneAndUpdate({mejl: mejl},{$set:{maticni_broj:parseInt(novo)}},(err,korisnik)=>{
                if(err) console.log(err);
                else res.json(korisnik);
            });
            break;
            default: break;
        }
    }

    azurirajPromenukorisnickogImena = (req:express.Request, res:express.Response)=>{
        let staro = req.body.staro;
        let novo = req.body.novo;

        aktivnost.updateMany({"korisnik": staro}, {$set:{"korisnik":novo}},(err1,r1)=>{
            if(err1) console.log(err1);
            poruka.updateMany({"korisnik": staro}, {$set:{"korisnik":novo}},(err2,r2)=>{
                if(err2) console.log(err2);
                ucesce.updateMany({"korisnik": staro}, {$set:{"korisnik":novo}},(err3,r3)=>{
                    if(err3) console.log(err3);
                    radionica.updateMany({"organizator": staro}, {$set:{"organizator":novo}},(err4,r4)=>{
                        if(err4) console.log(err4);
                        radionica.updateMany({"rezervisani": staro}, {$set:{"rezervisani.$":novo}},(err5,r5)=>{
                            if(err5) console.log(err5);
                            res.json(r5);
                        })
                    })
                })
            })
            
        })
    }

    azurirajPromenuMejla= (req:express.Request, res:express.Response)=>{
        let staro = req.body.staro;
        let novo = req.body.novo;
 
        radionica.updateMany({"naCekanju": staro}, {$set:{"naCekanju.$":novo}},(err5,r5)=>{
            if(err5) console.log(err5);
            res.json(r5);
        })
   
    }

    postaviStatus = (req:express.Request, res:express.Response)=>{
        let korisnicko_ime = req.body.korisnicko_ime;
        let status = req.body.status;

        korisnik.findOneAndUpdate({korisnicko_ime: korisnicko_ime},{$set:{"status":status}},(err,korisnik)=>{
            if(err) console.log(err);
            else res.json(korisnik);
        })
    }

    obrisiKorisnika = (req:express.Request, res:express.Response)=>{
        let kor = req.body.korisnik;
        let mejl = req.body.mejl;

        korisnik.deleteOne({"korisnicko_ime": kor},(err1,r1)=>{
            if(err1) console.log(err1);  
            poruka.deleteMany({"korisnik": kor}, (err3,r3)=>{
                if(err3) console.log(err3);
                aktivnost.deleteMany({"korisnik": kor}, (err2,r2)=>{
                    if(err2) console.log(err2);
                    ucesce.deleteMany({"korisnik": kor}, (err4,r4)=>{
                        if(err4) console.log(err4);
                        radionica.updateMany({"rezervisani": kor}, {$pull:{"rezervisani":kor}, $inc:{"preostalo_mesta": 1}},(err5,r5)=>{
                            if(err5) console.log(err5);
                            radionica.updateMany({"naCekanju": mejl}, {$pull:{"naCekanju":mejl}},(err6,r6)=>{
                                if(err5) console.log(err5);
                                res.json(r6);
                            })
                        })
                    })
                })
            })     
        })
    }

    unaprediUOrganizatora = (req:express.Request, res:express.Response)=>{
        let korisnicko_ime = req.body.korisnicko_ime;

        korisnik.findOneAndUpdate({korisnicko_ime: korisnicko_ime},{$set:{"tip":"organizator"}},(err,korisnik)=>{
            if(err) console.log(err);
            else res.json(korisnik);
        })
    }

}