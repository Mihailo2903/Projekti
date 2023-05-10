import express from 'express';
import { RadionicaController } from '../controllers/radionice.controller';

const radionicaRouter = express.Router();

radionicaRouter.route('/dohvatiRadionice').get(
    (req,res)=> new RadionicaController().dohvatiRadionice(req,res)
)

radionicaRouter.route('/dohvatiRadionicu').post(
    (req,res)=> new RadionicaController().dohvatiRadionicu(req,res)
)

radionicaRouter.route('/dohvatiRadionicePoNazivu').post(
    (req,res)=> new RadionicaController().dohvatiRadionicePoNazivu(req,res)
)

radionicaRouter.route('/dohvatiRadionicePoMestu').post(
    (req,res)=> new RadionicaController().dohvatiRadionicePoMestu(req,res)
)

radionicaRouter.route('/dohvatiRadionicePoOba').post(
    (req,res)=> new RadionicaController().dohvatiRadionicePoNazivuIMestu(req,res)
)

radionicaRouter.route('/dohvatiTop5').get(
    (req,res)=> new RadionicaController().dohvatiTop5(req,res)
)

radionicaRouter.route('/dohvatiIstoriju').post(
    (req,res)=> new RadionicaController().dohvatiIstoriju(req,res)
)

radionicaRouter.route('/dohvatiMojeRadionice').post(
    (req,res)=> new RadionicaController().dohvatiMojeRadionice(req,res)
)

radionicaRouter.route('/dohvatiAktivnosti').post(
    (req,res)=> new RadionicaController().dohvatiAktivnosti(req,res)
)

radionicaRouter.route('/obrisiAktivnost').post(
    (req,res)=> new RadionicaController().obrisiAktivnost(req,res)
)

radionicaRouter.route('/azurirajAktivnost').post(
    (req,res)=> new RadionicaController().azurirajAktivnost(req,res)
)

radionicaRouter.route('/dohvatiPoruke').post(
    (req,res)=> new RadionicaController().dohvatiPoruke(req,res)
)

radionicaRouter.route('/posaljiPoruku').post(
    (req,res)=> new RadionicaController().posaljiPoruku(req,res)
)

radionicaRouter.route('/otkaziUcesceNaRadionici').post(
    (req,res)=> new RadionicaController().otkaziUcesceNaRadionici(req,res)
)

radionicaRouter.route('/proveriUcesce').post(
    (req,res)=> new RadionicaController().proveriUcesceNaRadionici(req,res)
)

radionicaRouter.route('/rezervisiMestoNaRadionici').post(
    (req,res)=> new RadionicaController().rezervisiMestoNaRadionici(req,res)
)

radionicaRouter.route('/dodajNaListuZaCekanje').post(
    (req,res)=> new RadionicaController().dodajNaListuZaCekanje(req,res)
)

radionicaRouter.route('/dohvatiRadioniceSaIstimNazivom').post(
    (req,res)=> new RadionicaController().dohvatiRadioniceSaIstimNazivom(req,res)
)

radionicaRouter.route('/dohvatiAktivnostiRadionice').post(
    (req,res)=> new RadionicaController().dohvatiAktivnostiRadionice(req,res)
)

radionicaRouter.route('/dodajAktivnost').post(
    (req,res)=> new RadionicaController().dodajAktivnost(req,res)
)

radionicaRouter.route('/dodajRadionicu').post(
    (req,res)=> new RadionicaController().dodajRadionicu(req,res)
)

radionicaRouter.route('/proveriPostojanjePredlogaRadionice').post(
    (req,res)=> new RadionicaController().proveriPostojanjePredlogaRadionice(req,res)
)

radionicaRouter.route('/azurirajRadionicu').post(
    (req,res)=> new RadionicaController().azurirajRadionicu(req,res)
)

radionicaRouter.route('/prihvatiRezervaciju').post(
    (req,res)=> new RadionicaController().prihvatiRezervaciju(req,res)
)

radionicaRouter.route('/dohvatiUcescaNaRadionici').post(
    (req,res)=> new RadionicaController().dohvatiUcescaNaRadionici(req,res)
)

radionicaRouter.route('/odbijRezervaciju').post(
    (req,res)=> new RadionicaController().odbijRezervaciju(req,res)
)

radionicaRouter.route('/otkaziRadionicu').post(
    (req,res)=> new RadionicaController().otkaziRadionicu(req,res)
)

radionicaRouter.route('/azurirajNaziveRadionice').post(
    (req,res)=> new RadionicaController().azurirajNaziveRadionice(req,res)
)

radionicaRouter.route('/dohvatiRadioniceOrganizatora').post(
    (req,res)=> new RadionicaController().dohvatiRadioniceOrganizatora(req,res)
)

radionicaRouter.route('/obrisiRadionicu').post(
    (req,res)=> new RadionicaController().obrisiRadionicu(req,res)
)

radionicaRouter.route('/dohvatiNeodobreneRadionice').get(
    (req,res)=> new RadionicaController().dohvatiNeodobreneRadionice(req,res)
)

radionicaRouter.route('/dohvatiSvaUcesca').get(
    (req,res)=> new RadionicaController().dohvatiSvaUcesca(req,res)
)

radionicaRouter.route('/postaviStatusRadionice').post(
    (req,res)=> new RadionicaController().postaviStatusRadionice(req,res)
)

radionicaRouter.route('/azurirajAktivnostiRadionicePosleBrisanjaKorisnika').post(
    (req,res)=> new RadionicaController().azurirajAktivnostiRadionicePosleBrisanjaKorisnika(req,res)
)

radionicaRouter.route('/dohvatiRadioniceNaCekanju').post(
    (req,res)=> new RadionicaController().dohvatiRadioniceNaCekanju(req,res)
)

radionicaRouter.route('/dohvatiRezervisaneRadionice').post(
    (req,res)=> new RadionicaController().dohvatiRezervisaneRadionice(req,res)
)

radionicaRouter.route('/otkaziPrijavuZaRadionicu').post(
    (req,res)=> new RadionicaController().otkaziPrijavuZaRadionicu(req,res)
)



export default radionicaRouter;