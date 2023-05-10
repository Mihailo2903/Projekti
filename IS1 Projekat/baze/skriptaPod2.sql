delete from transakcija where IdTransakcija>7;
delete from komitent where IdKomitent>7;
delete from isplata where IdTransakcija>7;
delete from uplata where IdTransakcija>7;
delete from prenos where IdTransakcija>7;
delete from racun where IdRacun>7;

alter table racun auto_increment=1;
alter table transakcija auto_increment=1;
alter table komitent auto_increment=1;