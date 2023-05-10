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

public class NitZahtev10 extends Thread {

    @Override
    public void run() {
        JMSContext context = connFactory.createContext();
        JMSProducer producer = context.createProducer();
        JMSConsumer consumer = context.createConsumer(queue1, "Broj = 10");

        while (true) {
            try {
                ObjectMessage obj = (ObjectMessage) consumer.receive();

                EntityManager em = emf.createEntityManager();
                StringBuilder builder= new StringBuilder();
                               
                List<Mesto> listaMes = em.createNamedQuery("Mesto.findAll", Mesto.class).getResultList();
                
                if (listaMes.isEmpty()) {                    
                    builder.append("Nema nijednog mesta u bazi");
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
                
                em.close();

                ObjectMessage objOdg = context.createObjectMessage();
                objOdg.setObject(builder.toString());
                objOdg.setIntProperty("Broj", 10);

                producer.send(queueOdg, objOdg);

            } catch (JMSException ex) {
                Logger.getLogger(NitZahtev10.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}