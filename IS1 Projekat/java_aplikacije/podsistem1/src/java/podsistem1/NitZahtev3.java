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

public class NitZahtev3 extends Thread {

    @Override
    public void run() {
        JMSContext context = connFactory.createContext();
        JMSProducer producer = context.createProducer();
        JMSConsumer consumer = context.createConsumer(queue1, "Broj = 3");
     
      
        while (true) {
            try {
                int idmes=0;
                ObjectMessage obj = (ObjectMessage) consumer.receive();
                
                EntityManager em = emf.createEntityManager();
                String odgovor;
                odgovor = "Komitent je uspesno kreiran";

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
                        idmes=m.getIdMesto();
                        
                        Komitent k= new Komitent();
                        k.setAdresa(adresa);
                        k.setNaziv(naziv);
                        k.setIdMesto(m);
                        
                        em.getTransaction().begin();
                        em.persist(k);
                        em.getTransaction().commit();

                    } finally {
                        if (em.getTransaction().isActive()) {
                            em.getTransaction().rollback();
                            odgovor = "Nije uspelo kreiranje komitenta";
                        }
                        em.close();
                    }
                }

                ObjectMessage objOdg = context.createObjectMessage();
                objOdg.setObject(odgovor);
                objOdg.setIntProperty("Broj", 3);
                
                ObjectMessage objOdg2 = context.createObjectMessage();
                objOdg2.setObject(odgovor);
                objOdg2.setIntProperty("Broj", 33);
                objOdg2.setIntProperty("IdMesto", idmes);

                producer.send(queueOdg, objOdg);
                producer.send(queueOdg, objOdg2);
                

            } catch (JMSException ex) {
                Logger.getLogger(NitZahtev3.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}