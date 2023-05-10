/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podsistem2;


import entiteti.Racun;
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

public class NitZahtev6 extends Thread {

    @Override
    public void run() {
        JMSContext context = connFactory.createContext();
        JMSProducer producer = context.createProducer();
        JMSConsumer consumer = context.createConsumer(queue2, "Broj = 6");

        while (true) {
            try {
                ObjectMessage obj = (ObjectMessage) consumer.receive();

                EntityManager em = emf.createEntityManager();
                String odgovor = "Racun uspesno zatvoren";
                            
                int idRac = obj.getIntProperty("IdRacun");
                          
                List<Racun> listaRac = em.createNamedQuery("Racun.findByIdRacun", Racun.class).setParameter("idRacun", idRac).getResultList();
                       
                if(!listaRac.isEmpty()) {
                   try {                        
                        Racun r=listaRac.get(0);                                                  
                        r.setStatus("Z");
                        
                        em.getTransaction().begin();
                        em.persist(r);
                        em.getTransaction().commit();

                    } finally {
                        if (em.getTransaction().isActive()) {
                            em.getTransaction().rollback();
                            odgovor="Nije uspelo zatvaranje";
                        }
                        em.close();
                    }
                }
                else{
                    odgovor="Racun sa tim brojem ne postoji";
                    em.close();
                }
               
                ObjectMessage objOdg2= context.createObjectMessage();
                objOdg2.setObject(odgovor);
                objOdg2.setIntProperty("Broj", 6);

                producer.send(queueOdg, objOdg2);

            } catch (JMSException ex) {
                Logger.getLogger(NitZahtev6.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}