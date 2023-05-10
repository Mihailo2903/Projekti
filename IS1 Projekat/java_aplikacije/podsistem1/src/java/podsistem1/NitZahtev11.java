/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podsistem1;

import entiteti.Filijala;
import entiteti.Komitent;
import entiteti.Mesto;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.ObjectMessage;
import javax.persistence.EntityManager;
import static podsistem1.Podsistem1.connFactory;
import static podsistem1.Podsistem1.queue1;
import static podsistem1.Podsistem1.queueOdg;
import static podsistem1.Podsistem1.emf;

public class NitZahtev11 extends Thread {

    @Override
    public void run() {
        JMSContext context = connFactory.createContext();
        JMSProducer producer = context.createProducer();
        JMSConsumer consumer = context.createConsumer(queue1, "Broj = 11");

        while (true) {
            try {
                ObjectMessage obj = (ObjectMessage) consumer.receive();

                EntityManager em = emf.createEntityManager();
                StringBuilder builder= new StringBuilder();
                               
                List<Filijala> listaFil = em.createNamedQuery("Filijala.findAll", Filijala.class).getResultList();
                
                if (listaFil.isEmpty()) {                    
                    builder.append("Nema nijedne filijale u bazi");
                }
                  
                else {
                   for(Filijala f: listaFil){
                       int id=f.getIdFilijala();
                       String naziv=f.getNaziv();
                       String adresa=f.getAdresa();
                       int broj=f.getIdMesto().getIdMesto();
                       
                       builder.append("IdFilijala: " + id + "?");
                       builder.append("Naziv: " + naziv + "?");
                       builder.append("Adresa: " + adresa + "?");
                       builder.append("IdMesto: " + broj + "?");
                       builder.append("--------------------?");
                       
                   }
                }
                
                em.close();

                ObjectMessage objOdg = context.createObjectMessage();
                objOdg.setObject(builder.toString());
                objOdg.setIntProperty("Broj", 11);

                producer.send(queueOdg, objOdg);

            } catch (JMSException ex) {
                Logger.getLogger(NitZahtev11.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}