/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podsistem2;

import entiteti.Komitent;
import entiteti.Racun;
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

public class NitZahtev5 extends Thread {

    @Override
    public void run() {
        JMSContext context = connFactory.createContext();
        JMSProducer producer = context.createProducer();
        JMSConsumer consumer = context.createConsumer(queue2, "Broj = 55");

        while (true) {
            try {
                ObjectMessage obj = (ObjectMessage) consumer.receive();

                EntityManager em = emf.createEntityManager();
                String odgovor = "Racun uspesno otvoren";
                
                String naziv = obj.getStringProperty("Naziv");
                int mesto = obj.getIntProperty("IdMesto");
                String minus = obj.getStringProperty("Minus");
                
                List<Komitent> listaKom = em.createNamedQuery("Komitent.findByNaziv", Komitent.class).setParameter("naziv", naziv).getResultList();
                       
                if(!listaKom.isEmpty()) {
                   try {
                        Racun r= new Racun();
                        Komitent k=listaKom.get(0);                          
                        
                        r.setBrojTransakcija(0);
                        r.setDozvoljeniMinus(new BigDecimal(minus));
                        r.setIdMesto(mesto);
                        r.setStatus("A");
                        r.setStanje(new BigDecimal(0));
                        r.setIdKomitent(k);
                        
                        Date date= new Date();
                        BigDecimal d=new BigDecimal(2);
                        
                        r.setDatumOtvaranja(date);
                        
                        em.getTransaction().begin();
                        em.persist(r);
                        em.getTransaction().commit();

                    } finally {
                        if (em.getTransaction().isActive()) {
                            em.getTransaction().rollback();
                            odgovor="Nije uspela transakcija";
                        }
                        em.close();
                    }
                }
                else{
                    odgovor="Komitent sa tim nazivom ne postoji";
                    em.close();
                }
               
                ObjectMessage objOdg2= context.createObjectMessage();
                objOdg2.setObject(odgovor);
                objOdg2.setIntProperty("Broj", 55);

                producer.send(queueOdg, objOdg2);

            } catch (JMSException ex) {
                Logger.getLogger(NitZahtev5.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
