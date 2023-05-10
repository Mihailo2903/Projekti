delete from filijala where IdFilijala>7;
delete from komitent where IdKomitent>7;
delete from mesto where IdMesto>6;

alter table mesto auto_increment=1;
alter table filijala auto_increment=1;
alter table komitent auto_increment=1;

/*insert INTO `filijala` (`Naziv`, `Adresa`,`IdMesto`) VALUES
 ("Fili Pp1", "Ulica42 33",4),
 ("Fili Zr1", "Ulica33 63",6),
 ("Fili Ue1", "Ulica53 42",5),
 ("Fili Ni1", "Ulica32 11",3),
 ("Fili Bg1", "Ulica190 111",1),
 ("Fili Ue2", "Ulica50 43",5),
 ("Fili Ni2", "Ulica31 22",3);*/