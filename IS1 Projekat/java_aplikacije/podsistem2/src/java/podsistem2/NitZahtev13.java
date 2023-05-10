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

public class NitZahtev13 extends Thread {

    @Override
    public void run() {
        JMSContext context = connFactory.createContext();
        JMSProducer producer = context.createProducer();
        JMSConsumer consumer = context.createConsumer(queue2, "Broj = 13");

        while (true) {
            try {
                ObjectMessage obj = (ObjectMessage) consumer.receive();

                EntityManager em = emf.createEntityManager();
                StringBuilder builder= new StringBuilder();
                
                int broj = obj.getIntProperty("IdKomitent");
                               
                List<Komitent> listaKom = em.createNamedQuery("Komitent.findByIdKomitent", Komitent.class).setParameter("idKomitent", broj).getResultList();
                
                
                if (listaKom.isEmpty()) {                    
                    builder.append("Nema komitenta sa tim identifikatorom");                
                }
                  
                else {
                    Komitent k=listaKom.get(0);
                   List<Racun> listaRac = em.createQuery("SELECT r FROM Racun r WHERE r.idKomitent = :idKomitent", Racun.class).setParameter("idKomitent", k).getResultList();
                    
                   for(Racun r: listaRac){
                       int id=r.getIdRacun();
                       double stanje= r.getStanje().doubleValue();
                       double minus = r.getDozvoljeniMinus().doubleValue();
                       Date d=r.getDatumOtvaranja();
                       int br= r.getBrojTransakcija();
                       String status=r.getStatus();
                       int idm=r.getIdMesto();
                       
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
                
                em.close();

                ObjectMessage objOdg = context.createObjectMessage();
                objOdg.setObject(builder.toString());
                objOdg.setIntProperty("Broj", 13);

                producer.send(queueOdg, objOdg);
              //  System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

            } catch (JMSException ex) {
                Logger.getLogger(NitZahtev13.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
