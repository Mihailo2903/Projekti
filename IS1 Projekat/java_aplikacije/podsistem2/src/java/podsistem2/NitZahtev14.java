/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podsistem2;

import entiteti.Isplata;
import entiteti.Komitent;
import entiteti.Prenos;
import entiteti.Racun;
import entiteti.Transakcija;
import entiteti.Uplata;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.ObjectMessage;
import javax.persistence.EntityManager;
import static podsistem2.Podsistem2.connFactory;
import static podsistem2.Podsistem2.queue2;
import static podsistem2.Podsistem2.queueOdg;
import static podsistem2.Podsistem2.emf;

public class NitZahtev14 extends Thread {

    @Override
    public void run() {
        JMSContext context = connFactory.createContext();
        JMSProducer producer = context.createProducer();
        JMSConsumer consumer = context.createConsumer(queue2, "Broj = 14");

        while (true) {
            try {
                ObjectMessage obj = (ObjectMessage) consumer.receive();

                EntityManager em = emf.createEntityManager();
                StringBuilder builder = new StringBuilder();

                int broj = obj.getIntProperty("IdRacun");

                List<Racun> listaRac = em.createNamedQuery("Racun.findByIdRacun", Racun.class).setParameter("idRacun", broj).getResultList();

                if (listaRac.isEmpty()) {
                    builder.append("Nema racuna sa tim brojem");
                } else {
                    
                    List<Transakcija> listaTra = em.createNamedQuery("Transakcija.findAll", Transakcija.class).getResultList();

                    for (Transakcija t : listaTra) {
                        if (t.getIdRacun().getIdRacun()== broj || (t.getPrenos() != null && t.getPrenos().getIdRacunSa().getIdRacun() == broj)) {
                            String vrsta;
                            if(t.getUplata()!=null) vrsta="Uplata:??";
                            else if(t.getIsplata()!=null) vrsta="Isplata:??";
                            else vrsta="Prenos:??";
                            
                            int id = t.getIdTransakcija();
                            int idrac = t.getIdRacun().getIdRacun();
                            double iznos = t.getIznos().doubleValue();
                            Date d = t.getDatumVreme();
                            int rb = t.getRedniBroj();
                            String svrha = t.getSvrha();
                            
                            
                            builder.append(vrsta);
                            builder.append("IdTransakcija: " + id + "?");
                            builder.append("IdRacun: " + idrac + "?");
                            builder.append("Redni broj na racunu: " + rb + "?");
                            builder.append("Datum i vreme izvrsenja: " + d + "?");
                            builder.append("Iznos: " + iznos + "?");
                            builder.append("Svrha: " + svrha + "?");
                            
                            if(t.getUplata()!=null){
                                builder.append("IdFilijala: " + t.getUplata().getIdFilijala() + "?");
                            }
                            if(t.getIsplata()!=null){
                                builder.append("IdFilijala: " + t.getIsplata().getIdFilijala() + "?");
                            }
                            if(t.getPrenos()!=null){
                                builder.append("IdRacunSa: " + t.getPrenos().getIdRacunSa().getIdRacun() + "?");
                            }
                            builder.append("--------------------?");
                        }

                    }
                }

                em.close();

                ObjectMessage objOdg = context.createObjectMessage();
                objOdg.setObject(builder.toString());
                objOdg.setIntProperty("Broj", 14);

                producer.send(queueOdg, objOdg);
                //  System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

            } catch (JMSException ex) {
                Logger.getLogger(NitZahtev14.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
