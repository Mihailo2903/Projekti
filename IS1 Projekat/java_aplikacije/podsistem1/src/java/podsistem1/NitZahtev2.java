/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podsistem1;

import entiteti.Filijala;
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

public class NitZahtev2 extends Thread {

    @Override
    public void run() {
        JMSContext context = connFactory.createContext();
        JMSProducer producer = context.createProducer();
        JMSConsumer consumer = context.createConsumer(queue1, "Broj = 2");

        while (true) {
            try {
                ObjectMessage obj = (ObjectMessage) consumer.receive();

                EntityManager em = emf.createEntityManager();
                String odgovor;
                odgovor = "Filijala je uspesno kreirana";

                String naziv = obj.getStringProperty("Naziv");
                String adresa = obj.getStringProperty("Adresa");
                String mesto = obj.getStringProperty("Mesto");

                List<Mesto> lista = em.createNamedQuery("Mesto.findByNaziv", Mesto.class).setParameter("naziv", mesto).getResultList();

                if (lista.isEmpty()) {
                    odgovor = "Nema mesta sa tim nazivom";
                    em.close();
                } 
                
                else {
                   try {
                        Mesto m= lista.get(0);
                        
                        Filijala f= new Filijala();
                        f.setNaziv(naziv);
                        f.setAdresa(adresa);
                        f.setIdMesto(m);
                        
                        em.getTransaction().begin();
                        em.persist(f);
                        em.getTransaction().commit();

                    } finally {
                        if (em.getTransaction().isActive()) {
                            em.getTransaction().rollback();
                            odgovor = "Nije uspelo kreiranje filijale";
                        }
                        em.close();
                    }
                }

                ObjectMessage objOdg = context.createObjectMessage();
                objOdg.setObject(odgovor);
                objOdg.setIntProperty("Broj", 2);

                producer.send(queueOdg, objOdg);

            } catch (JMSException ex) {
                Logger.getLogger(NitZahtev2.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
