/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podsistem1;

import entiteti.Mesto;
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

public class NitZahtev1 extends Thread {

    @Override
    public void run() {
        JMSContext context = connFactory.createContext();
        JMSProducer producer = context.createProducer();
        JMSConsumer consumer = context.createConsumer(queue1,"Broj = 1");
        
       // System.out.println("Krenula nit");

        while (true) {
            try {
                ObjectMessage obj = (ObjectMessage) consumer.receive();
                
               // System.out.println("Primio poruku");

                EntityManager em = emf.createEntityManager();
                String odgovor;
                odgovor="Mesto je uspesno kreirano";

                int posBroj = obj.getIntProperty("PosBroj");
                String naziv = obj.getStringProperty("Naziv");

                try {
                    Mesto m = new Mesto();

                    m.setNaziv(naziv);
                    m.setPostanskiBroj(posBroj);

                    em.getTransaction().begin();

                    em.persist(m);

                    em.getTransaction().commit();
                } finally {
                    if (em.getTransaction().isActive()) {
                        em.getTransaction().rollback();
                        odgovor="Nije uspelo kreiranje mesta";
                    }
                    em.close();
                }
                
                //System.out.println("Napravio");
                
                ObjectMessage objOdg=context.createObjectMessage();
                objOdg.setObject(odgovor);
                objOdg.setIntProperty("Broj", 1);
                
                producer.send(queueOdg, objOdg);

            } catch (JMSException ex) {
                Logger.getLogger(NitZahtev1.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
