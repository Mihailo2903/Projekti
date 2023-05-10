/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podsistem2;

import entiteti.Isplata;
import entiteti.Prenos;
import entiteti.Racun;
import entiteti.Transakcija;
import entiteti.Uplata;
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

public class NitZahtev9 extends Thread {

    @Override
    public void run() {
        JMSContext context = connFactory.createContext();
        JMSProducer producer = context.createProducer();
        JMSConsumer consumer = context.createConsumer(queue2, "Broj = 99");

        while (true) {
            try {
                ObjectMessage obj = (ObjectMessage) consumer.receive();

                EntityManager em = emf.createEntityManager();
                String odgovor = "Isplata uspesno izvrsena";

                int idFil = obj.getIntProperty("IdFilijala");
                int idRac = obj.getIntProperty("IdRacun");
                String iznos = obj.getStringProperty("Iznos");
                String svrha = obj.getStringProperty("Svrha");
         
                List<Racun> listaRac = em.createNamedQuery("Racun.findByIdRacun", Racun.class).setParameter("idRacun", idRac).getResultList();

                if (listaRac.isEmpty()) {
                    odgovor = "Nema racuna sa tim brojem";
                    em.close();
                } 
                else {
                    try {
                        Racun rac = listaRac.get(0);


                        if (rac.getStatus().equals("Z")) {
                            odgovor = "Racun sa kog se vrsi isplata je zatvoren";
                        } 
                        else if(rac.getStatus().equals("B")){
                            odgovor = "Racun sa kog se vrsi isplata je blokiran";
                        }
                        else {
                            Transakcija t = new Transakcija();

                            BigDecimal izn = new BigDecimal(iznos);
                            t.setIznos(izn);
                            Date d = new Date();
                            t.setDatumVreme(d);
                            t.setSvrha(svrha);
                            t.setIdRacun(rac);
                            int broj = rac.getBrojTransakcija();
                            t.setRedniBroj(broj + 1);
                            
                            Isplata i= new Isplata();
                            i.setIdFilijala(idFil);
                            
                            double iznoss = izn.doubleValue();

                            double stanje = rac.getStanje().doubleValue();
                            double dozvMinus = rac.getDozvoljeniMinus().doubleValue();
     
                            double novo = stanje - iznoss;

                            if (novo < -dozvMinus) {
                                rac.setStatus("B");
                            }
                          
                            rac.setStanje(new BigDecimal(novo + ""));

                            rac.setBrojTransakcija(rac.getBrojTransakcija() + 1);

                            em.getTransaction().begin();
                            em.persist(t);
                            em.getTransaction().commit();

                            em.clear();

                            List<Transakcija> l = em.createQuery("select m from Transakcija m order by m.idTransakcija desc ", Transakcija.class).getResultList();
                                                  
                            i.setIdTransakcija(l.get(0).getIdTransakcija());
                          
                            em.getTransaction().begin();
                            em.persist(i);
                            em.getTransaction().commit();

                        }

                    } finally {
                        if (em.getTransaction().isActive()) {
                            em.getTransaction().rollback();
                            odgovor = "Nije uspela isplata";
                        }
                        em.close();
                    }
                }

                ObjectMessage objOdg2 = context.createObjectMessage();
                objOdg2.setObject(odgovor);
                objOdg2.setIntProperty("Broj", 99);

                producer.send(queueOdg, objOdg2);

            } catch (JMSException ex) {
                Logger.getLogger(NitZahtev9.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
