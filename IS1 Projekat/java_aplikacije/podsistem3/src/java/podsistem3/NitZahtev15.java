/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podsistem3;

import entiteti.Filijala;
import entiteti.Isplata;
import entiteti.Komitent;
import entiteti.Mesto;
import entiteti.Prenos;
import entiteti.Racun;
import entiteti.Transakcija;
import entiteti.Uplata;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.ObjectMessage;
import javax.persistence.EntityManager;
import static podsistem3.Podsistem3.connFactory;
import static podsistem3.Podsistem3.emf;
import static podsistem3.Podsistem3.queue2;
import static podsistem3.Podsistem3.queueOdg;
import static podsistem3.Podsistem3.queue1;
import static podsistem3.Podsistem3.queue3;

/**
 *
 * @author Mico
 */
public class NitZahtev15 extends Thread {

    @Override
    public void run() {
        JMSContext context = connFactory.createContext();
        JMSProducer producer = context.createProducer();
        JMSConsumer consumer = context.createConsumer(queue3, "Broj = 15");

         while (true) {
            try {
                ObjectMessage obj = (ObjectMessage) consumer.receive();

                EntityManager em = emf.createEntityManager();
                StringBuilder builder= new StringBuilder();
                
                builder.append("Mesta:??");                              
                List<Mesto> listaMes = em.createNamedQuery("Mesto.findAll", Mesto.class).getResultList();
                               
                if (listaMes.isEmpty()) {                    
                    builder.append("Nema nijednog mesta u bazi?");
                }
                  
                else {
                   for(Mesto m: listaMes){
                       int id=m.getIdMesto();
                       String naziv=m.getNaziv();
                       int broj=m.getPostanskiBroj();
                       
                       builder.append("IdMesto: " + id + "?");
                       builder.append("Postanski broj: " + broj + "?");
                       builder.append("Naziv: " + naziv + "?");
                       builder.append("--------------------?");
                       
                   }
                }
                
                builder.append("####################?");
                
                builder.append("Filijale:??");               
                List<Filijala> listaFil = em.createNamedQuery("Filijala.findAll", Filijala.class).getResultList();
                
                if (listaFil.isEmpty()) {                    
                    builder.append("Nema nijedne filijale u bazi?");
                }
                  
                else {
                   for(Filijala f: listaFil){
                       int id=f.getIdFilijala();
                       String naziv=f.getNaziv();
                       String adresa=f.getAdresa();
                       int broj=f.getIdMesto();
                       
                       builder.append("IdFilijala: " + id + "?");
                       builder.append("Naziv: " + naziv + "?");
                       builder.append("Adresa: " + adresa + "?");
                       builder.append("IdMesto: " + broj + "?");
                       builder.append("--------------------?");
                       
                   }
                }
                
                builder.append("####################?");
                                
                builder.append("Komitenti:??");
                List<Komitent> listaKom = em.createNamedQuery("Komitent.findAll", Komitent.class).getResultList();
                
                if (listaKom.isEmpty()) {                    
                    builder.append("Nema nijednog komitenta u bazi?");                
                }
                  
                else {
                   for(Komitent k: listaKom){
                       int id=k.getIdKomitent();
                       String naziv=k.getNaziv();
                       String adresa=k.getAdresa();
                       int broj=k.getIdMesto();
                       
                       builder.append("IdKomitent: " + id + "?");
                       builder.append("Naziv: " + naziv + "?");
                       builder.append("Adresa: " + adresa + "?");
                       builder.append("IdMesto: " + broj + "?");
                       builder.append("--------------------?");
                       
                   }
                }
                
                builder.append("####################?");
      
                
                builder.append("Racuni:??");
                List<Racun> listaRac = em.createNamedQuery("Racun.findAll", Racun.class).getResultList();
                
                
                if (listaRac.isEmpty()) {                    
                    builder.append("Nema nijedan racun u bazi?");                
                }
                  
                else {                   
                   for(Racun r: listaRac){
                       int id=r.getIdRacun();
                       double stanje= r.getStanje().doubleValue();
                       double minus = r.getDozvoljeniMinus().doubleValue();
                       Date d=r.getDatumOtvaranja();
                       int br= r.getBrojTransakcija();
                       String status=r.getStatus();
                       int idm=r.getIdMesto();
                       int broj=r.getIdKomitent();
                       
                       builder.append("IdRacun: " + id + "?");
                       builder.append("Stanje: " + stanje + "?");
                       builder.append("Dozvoljeni minus: " + minus + "?");
                       builder.append("Status: " + status + "?");
                       builder.append("Datum otvaranja: " + d + "?");
                       builder.append("Broj transakcija: " + br + "?");
                       builder.append("IdKomitent: " + broj + "?");
                       builder.append("IdMesto: " + idm + "?");
                       builder.append("--------------------?");
                       
                   }
                }
                
                builder.append("####################?");
                
                
                builder.append("Transakcije:??");
                List<Transakcija> listaTra = em.createNamedQuery("Transakcija.findAll", Transakcija.class).getResultList();
                
                
                if (listaTra.isEmpty()) {                    
                    builder.append("Nema nijedna transakcija u bazi?");                
                }
                  
                else {                   
                   for(Transakcija t: listaTra){
                    int id = t.getIdTransakcija();
                    int idrac = t.getIdRacun();
                    double iznos = t.getIznos().doubleValue();
                    Date d = t.getDatumVreme();
                    int rb = t.getRedniBroj();
                    String svrha = t.getSvrha();

                    builder.append("IdTransakcija: " + id + "?");
                    builder.append("IdRacun: " + idrac + "?");
                    builder.append("Redni broj na racunu: " + rb + "?");
                    builder.append("Datum i vreme izvrsenja: " + d + "?");
                    builder.append("Iznos: " + iznos + "?");
                    builder.append("Svrha: " + svrha + "?");
                    builder.append("--------------------?");
                       
                   }
                }
                
                builder.append("####################?");
                
                
                builder.append("Uplate:??");
                List<Uplata> listaUpl = em.createNamedQuery("Uplata.findAll", Uplata.class).getResultList();
                
                
                if (listaUpl.isEmpty()) {                    
                    builder.append("Nema nijedna uplata u bazi?");                
                }
                  
                else {                   
                   for(Uplata u: listaUpl){
                    int id = u.getIdTransakcija();
                    int idfil = u.getIdFilijala();
                   
                    builder.append("IdTransakcija: " + id + "?");
                    builder.append("IdFilijala: " + idfil + "?");
                    builder.append("--------------------?");
                   }
                }
                
                builder.append("####################?");
                
                
                builder.append("Isplate:??");
                List<Isplata> listaIsp = em.createNamedQuery("Isplata.findAll", Isplata.class).getResultList();
                
                
                if (listaIsp.isEmpty()) {                    
                    builder.append("Nema nijedna isplata u bazi?");                
                }
                  
                else {                   
                   for(Isplata i: listaIsp){
                    int id = i.getIdTransakcija();
                    int idfil = i.getIdFilijala();
                   
                    builder.append("IdTransakcija: " + id + "?");
                    builder.append("IdFilijala: " + idfil + "?");
                    builder.append("--------------------?");
                   }
                }
                
                builder.append("####################?");
                
                
                builder.append("Prenosi:??");
                List<Prenos> listaPre = em.createNamedQuery("Prenos.findAll", Prenos.class).getResultList();
                
                
                if (listaPre.isEmpty()) {                    
                    builder.append("Nema nijedan prenos u bazi?");                
                }
                  
                else {                   
                   for(Prenos p: listaPre){
                    int id = p.getIdTransakcija();
                    int idrac = p.getIdRacunSa();
                   
                    builder.append("IdTransakcija: " + id + "?");
                    builder.append("IdRacunSa: " + idrac + "?");
                    builder.append("--------------------?");
                   }
                }
                
                builder.append("####################?");
                
                em.close();

                ObjectMessage objOdg = context.createObjectMessage();
                objOdg.setObject(builder.toString());
                objOdg.setIntProperty("Broj", 15);

                producer.send(queueOdg, objOdg);

            } catch (JMSException ex) {
                Logger.getLogger(NitZahtev15.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
