import express from 'express';
import { KorisnikController } from '../controllers/korisnik.controller';


const korisnikRouter = express.Router();

korisnikRouter.route('/registrujUcesnika').post(
    (req,res)=> new KorisnikController().registrujUcesnika(req,res)
)

korisnikRouter.route('/registrujOrganizatora').post(
    (req,res)=> new KorisnikController().registrujOrganizatora(req,res)
)

korisnikRouter.route('/proveriJedinstvenost').post(
    (req,res)=> new KorisnikController().proveraJedinstvenosti(req,res)
)

korisnikRouter.route('/ulogujSe').post(
    (req,res)=> new KorisnikController().ulogujSe(req,res)
)

korisnikRouter.route('/posaljiMejl').post(
    (req,res)=> new KorisnikController().posaljiMejl(req,res)
)

korisnikRouter.route('/dohvatiPoMejlu').post(
    (req,res)=> new KorisnikController().dohvatiPoMejlu(req,res)
)

korisnikRouter.route('/dohvatiPoKorImenu').post(
    (req,res)=> new KorisnikController().dohvatiPoKorImenu(req,res)
)

korisnikRouter.route('/promeniLozinku').post(
    (req,res)=> new KorisnikController().promeniLozinku(req,res)
)

korisnikRouter.route('/promeniPodatak').post(
    (req,res)=> new KorisnikController().promeniPodatak(req,res)
)

korisnikRouter.route('/posaljiObavestenjeOSlobodnomMestu').post(
    (req,res)=> new KorisnikController().posaljiObavestenjeOSlobodnomMestu(req,res)
)

korisnikRouter.route('/posaljiObavestenjeOOtkazivanju').post(
    (req,res)=> new KorisnikController().posaljiObavestenjeOOtkazivanju(req,res)
)

korisnikRouter.route('/azurirajPromenukorisnickogImena').post(
    (req,res)=> new KorisnikController().azurirajPromenukorisnickogImena(req,res)
)

korisnikRouter.route('/azurirajPromenuMejla').post(
    (req,res)=> new KorisnikController().azurirajPromenuMejla(req,res)
)

korisnikRouter.route('/dohvatSveKorisnike').get(
    (req,res)=> new KorisnikController().dohvatSveKorisnike(req,res)
)

korisnikRouter.route('/dohvatSveUcesnike').get(
    (req,res)=> new KorisnikController().dohvatSveUcesnike(req,res)
)

korisnikRouter.route('/postaviStatus').post(
    (req,res)=> new KorisnikController().postaviStatus(req,res)
)

korisnikRouter.route('/obrisiKorisnika').post(
    (req,res)=> new KorisnikController().obrisiKorisnika(req,res)
)

korisnikRouter.route('/unaprediUOrganizatora').post(
    (req,res)=> new KorisnikController().unaprediUOrganizatora(req,res)
)

export default korisnikRouter;