import express from 'express';
import cors from 'cors'; //zbog razlicitih portova front i back
import bodyParser from 'body-parser'; //da bi imali body
import mongoose from 'mongoose'; //za mongodb
import korisnikRouter from './routers/korisni.router';
import radionicaRouter from './routers/radionice.routers';

const app = express();
app.use(cors());
app.use(bodyParser());

mongoose.connect('mongodb://localhost:27017/pia_projekat');
const connection = mongoose.connection;

connection.once('open', ()=>{
    console.log('db connection ok')
})

const router = express.Router();
router.use('/korisnici', korisnikRouter);
router.use('/radionice', radionicaRouter);

app.use("/", router);
app.listen(4000, () => console.log(`Express server running on port 4000`));