/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podsistem2;


import entiteti.Komitent;
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

public class NitZahtev4 extends Thread {

    @Override
    public void run() {
        JMSContext context = connFactory.createContext();
        JMSProducer producer = context.createProducer();
        JMSConsumer consumer = context.createConsumer(queue2, "Broj = 44");

        while (true) {
            try {
                ObjectMessage obj = (ObjectMessage) consumer.receive();

                EntityManager em = emf.createEntityManager();
                
                String naziv = obj.getStringProperty("Naziv");
                int mesto = obj.getIntProperty("IdMesto");
                
                List<Komitent> listaKom = em.createNamedQuery("Komitent.findByNaziv", Komitent.class).setParameter("naziv", naziv).getResultList();
                       
                if(!listaKom.isEmpty()) {
                   try {                     
                        Komitent k=listaKom.get(0);                          
                        k.setIdMesto(mesto);
                        
                        em.getTransaction().begin();
                        em.persist(k);
                        em.getTransaction().commit();

                    } finally {
                        if (em.getTransaction().isActive()) {
                            em.getTransaction().rollback();
                        }
                        em.close();
                    }
                }
               
                ObjectMessage objOdg2= context.createObjectMessage();
                objOdg2.setIntProperty("Broj", 44);

                producer.send(queueOdg, objOdg2);

            } catch (JMSException ex) {
                Logger.getLogger(NitZahtev4.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}