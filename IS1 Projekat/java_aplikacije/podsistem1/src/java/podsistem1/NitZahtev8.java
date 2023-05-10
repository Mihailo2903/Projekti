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

public class NitZahtev8 extends Thread {

    @Override
    public void run() {
        JMSContext context = connFactory.createContext();
        JMSProducer producer = context.createProducer();
        JMSConsumer consumer = context.createConsumer(queue1, "Broj = 88");

        while (true) {
            try {
                int idmes=0;
                ObjectMessage obj = (ObjectMessage) consumer.receive();

                EntityManager em = emf.createEntityManager();             
               
                String filijala = obj.getStringProperty("Filijala");
                
                List<Filijala> listaFil = em.createNamedQuery("Filijala.findByNaziv", Filijala.class).setParameter("naziv", filijala).getResultList();
          
                if (!listaFil.isEmpty()) {               
                    Filijala f= listaFil.get(0);                    
                    idmes=f.getIdFilijala();
                }
              
                ObjectMessage objOdg2= context.createObjectMessage();
                objOdg2.setIntProperty("IdFilijala", idmes);
                objOdg2.setIntProperty("Broj", 88);

                producer.send(queueOdg, objOdg2);

            } catch (JMSException ex) {
                Logger.getLogger(NitZahtev8.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
