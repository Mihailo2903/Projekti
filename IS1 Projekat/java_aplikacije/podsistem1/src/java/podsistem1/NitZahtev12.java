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

public class NitZahtev12 extends Thread {

    @Override
    public void run() {
        JMSContext context = connFactory.createContext();
        JMSProducer producer = context.createProducer();
        JMSConsumer consumer = context.createConsumer(queue1, "Broj = 12");

        while (true) {
            try {
                ObjectMessage obj = (ObjectMessage) consumer.receive();

                EntityManager em = emf.createEntityManager();
                StringBuilder builder= new StringBuilder();
                               
                List<Komitent> listaKom = em.createNamedQuery("Komitent.findAll", Komitent.class).getResultList();
                
                if (listaKom.isEmpty()) {                    
                    builder.append("Nema nijednog komitenta u bazi");                
                }
                  
                else {
                   for(Komitent k: listaKom){
                       int id=k.getIdKomitent();
                       String naziv=k.getNaziv();
                       String adresa=k.getAdresa();
                       int broj=k.getIdMesto().getIdMesto();
                       
                       builder.append("IdKomitent: " + id + "?");
                       builder.append("Naziv: " + naziv + "?");
                       builder.append("Adresa: " + adresa + "?");
                       builder.append("IdMesto: " + broj + "?");
                       builder.append("--------------------?");
                       
                   }
                }
                
                em.close();

                ObjectMessage objOdg = context.createObjectMessage();
                objOdg.setObject(builder.toString());
                objOdg.setIntProperty("Broj", 12);

                producer.send(queueOdg, objOdg);

            } catch (JMSException ex) {
                Logger.getLogger(NitZahtev12.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}