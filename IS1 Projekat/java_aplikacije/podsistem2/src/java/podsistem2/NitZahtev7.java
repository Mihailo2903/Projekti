/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podsistem2;

import entiteti.Prenos;
import entiteti.Racun;
import entiteti.Transakcija;
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

public class NitZahtev7 extends Thread {

    @Override
    public void run() {
        JMSContext context = connFactory.createContext();
        JMSProducer producer = context.createProducer();
        JMSConsumer consumer = context.createConsumer(queue2, "Broj = 7");

        while (true) {
            try {
                ObjectMessage obj = (ObjectMessage) consumer.receive();

                EntityManager em = emf.createEntityManager();
                String odgovor = "Prenos uspesno izvrsen";

                int idRacNa = obj.getIntProperty("IdRacunNa");
                int idRacSa = obj.getIntProperty("IdRacunSa");
                String iznos = obj.getStringProperty("Iznos");
                String svrha = obj.getStringProperty("Svrha");

                List<Racun> listaRacNa = em.createNamedQuery("Racun.findByIdRacun", Racun.class).setParameter("idRacun", idRacNa).getResultList();
                List<Racun> listaRacSa = em.createNamedQuery("Racun.findByIdRacun", Racun.class).setParameter("idRacun", idRacSa).getResultList();

                if (listaRacNa.isEmpty()) {
                    odgovor = "Racun na koji se vrsi uplata ne postoji u bazi";
                    em.close();
                } else if (listaRacSa.isEmpty()) {
                    odgovor = "Racun sa kog se vrsi prenos ne postoji u bazi";
                    em.close();
                } else {
                    try {
                        Racun racNa = listaRacNa.get(0);
                        Racun racSa = listaRacSa.get(0);

                        if (racNa.getStatus().equals("Z")) {
                            odgovor = "Racun na koji se vrsi prenos zatvoren";
                        } else if (racSa.getStatus().equals("Z")) {
                            odgovor = "Racun sa kog se vrsi prenos zatvoren";
                        } else if (racSa.getStatus().equals("B")) {
                            odgovor = "Racun sa kog se vrsi prenos blokiran";
                        } else {
                            Transakcija t = new Transakcija();

                            BigDecimal izn = new BigDecimal(iznos);
                            t.setIznos(izn);
                            Date d = new Date();
                            t.setDatumVreme(d);
                            t.setSvrha(svrha);
                            t.setIdRacun(racNa);
                            int broj = racNa.getBrojTransakcija();
                            t.setRedniBroj(broj + 1);
                            
                            Prenos p= new Prenos();
                            p.setIdRacunSa(racSa);
                            double iznoss = izn.doubleValue();

                            double stanje1 = racNa.getStanje().doubleValue();
                            double stanje2 = racSa.getStanje().doubleValue();
                            double dozvMinus1 = racNa.getDozvoljeniMinus().doubleValue();
                            double dozvMinus2 = racSa.getDozvoljeniMinus().doubleValue();

                            double novo1 = stanje1 + iznoss;
                            double novo2 = stanje2 - iznoss;

                            if (novo1 >= -dozvMinus1) {
                                racNa.setStatus("A");
                            }
                            if (novo2 < -dozvMinus2) {
                                racSa.setStatus("B");
                            }

                            racNa.setStanje(new BigDecimal(novo1 + ""));
                            racSa.setStanje(new BigDecimal(novo2 + ""));

                            racNa.setBrojTransakcija(racNa.getBrojTransakcija() + 1);
                            racSa.setBrojTransakcija(racSa.getBrojTransakcija() + 1);

                            em.getTransaction().begin();
                            em.persist(t);
                            em.getTransaction().commit();

                            em.clear();

                            List<Transakcija> l = em.createQuery("select m from Transakcija m order by m.idTransakcija desc ", Transakcija.class).getResultList();
                           // System.out.print(l.get(0));
                            
                            p.setIdTransakcija(l.get(0).getIdTransakcija());
                          
                            em.getTransaction().begin();
                            em.persist(p);
                            em.getTransaction().commit();

                        }

                    } finally {
                        if (em.getTransaction().isActive()) {
                            em.getTransaction().rollback();
                            odgovor = "Nije uspeo prenos";
                        }
                        em.close();
                    }
                }

                ObjectMessage objOdg2 = context.createObjectMessage();
                objOdg2.setObject(odgovor);
                objOdg2.setIntProperty("Broj", 7);

                producer.send(queueOdg, objOdg2);

            } catch (JMSException ex) {
                Logger.getLogger(NitZahtev7.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
