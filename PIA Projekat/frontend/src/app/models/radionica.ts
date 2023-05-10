export class Radionica {
    _id: string;
    organizator: string; 
    naziv:  string;    
    datum:  Date;  
    mesto:  string;   
    kratak_opis: string;    
    duzi_opis: string;    
    glavna_slika:  string;    
    slike:   Array<string>;    
    preostalo_mesta: number;    
    rezervisani:  Array<string>;   
    naCekanju: Array<string>;   
    broj_komentara: number;   
    broj_lajkova: number;
    status: string;
    
    zavrsena: boolean;
    uslov: boolean;
}
