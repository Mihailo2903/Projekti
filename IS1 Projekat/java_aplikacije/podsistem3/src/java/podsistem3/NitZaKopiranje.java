/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podsistem3;

import entiteti.Filijala;
import entiteti.Isplata;
import entiteti.Komitent;
import entiteti.Mesto;
import entiteti.Prenos;
import entiteti.Racun;
import entiteti.Transakcija;
import entiteti.Uplata;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.ObjectMessage;
import javax.persistence.EntityManager;
import static podsistem3.Podsistem3.connFactory;
import static podsistem3.Podsistem3.emf;
import static podsistem3.Podsistem3.queue2;
import static podsistem3.Podsistem3.queueOdg;
import static podsistem3.Podsistem3.queue1;

/**
 *
 * @author Mico
 */
public class NitZaKopiranje extends Thread {

    @Override
    public void run() {
        JMSContext context = connFactory.createContext();
        JMSProducer producer = context.createProducer();
        JMSConsumer consumer1 = context.createConsumer(queueOdg, "Broj = 101");
        JMSConsumer consumer2 = context.createConsumer(queueOdg, "Broj = 102");

        while (true) {
            try {
                ObjectMessage obj1 = context.createObjectMessage();
                obj1.setIntProperty("Broj", 101);
                producer.send(queue1, obj1);

                ObjectMessage obj2 = context.createObjectMessage();
                obj2.setIntProperty("Broj", 102);
                producer.send(queue2, obj2);

                ObjectMessage poruka1 = (ObjectMessage) consumer1.receive();
                ObjectMessage poruka2 = (ObjectMessage) consumer2.receive();

                EntityManager em = emf.createEntityManager();

                ArrayList<ArrayList<String>> lista1 = (ArrayList<ArrayList<String>>) poruka1.getObject();
                ArrayList<ArrayList<String>> lista2 = (ArrayList<ArrayList<String>>) poruka2.getObject();

                ArrayList<String> mesta = lista1.get(0);
                ArrayList<String> komitenti = lista1.get(1);
                ArrayList<String> filijale = lista1.get(2);

                ArrayList<String> racuni = lista2.get(0);
                ArrayList<String> transakcije = lista2.get(1);
                ArrayList<String> uplate = lista2.get(2);
                ArrayList<String> isplate = lista2.get(3);
                ArrayList<String> prenosi = lista2.get(4);

                List<Mesto> listaMes = em.createNamedQuery("Mesto.findAll", Mesto.class).getResultList();

                try {
                    em.getTransaction().begin();
                    for (String mes : mesta) {
                        boolean ima = false;
                        StringTokenizer stringTokenizer = new StringTokenizer(mes, "?");
                        int idmes = Integer.parseInt(stringTokenizer.nextToken());
                        String naziv = stringTokenizer.nextToken();
                        int broj = Integer.parseInt(stringTokenizer.nextToken());

                        for (Mesto m : listaMes) {
                            if (m.getIdMesto() == idmes) {
                                ima = true;
                                break;
                            }
                        }

                        if (ima == false) {
                            Mesto m = new Mesto();
                            m.setIdMesto(idmes);
                            m.setNaziv(naziv);
                            m.setPostanskiBroj(broj);

                            em.persist(m);
                        }
                    }
                    em.getTransaction().commit();
                } finally {
                    if (em.getTransaction().isActive()) {
                        em.getTransaction().rollback();
                    }
                }

                em.clear();

                List<Komitent> listaKom = em.createNamedQuery("Komitent.findAll", Komitent.class).getResultList();

                try {
                    em.getTransaction().begin();
                    for (String kom : komitenti) {
                        boolean ima = false;
                        StringTokenizer stringTokenizer = new StringTokenizer(kom, "?");
                        int idkom = Integer.parseInt(stringTokenizer.nextToken());
                        String naziv = stringTokenizer.nextToken();
                        String adresa = stringTokenizer.nextToken();
                        int idmes = Integer.parseInt(stringTokenizer.nextToken());

                        for (Komitent k : listaKom) {
                            if (k.getIdKomitent() == idkom) {
                                ima = true;
                                if (k.getIdMesto() != idmes) {
                                    k.setIdMesto(idmes);                                   
                                }
                                break;
                            }
                        }

                        if (ima == false) {
                            Komitent k=new Komitent();
                            k.setIdKomitent(idkom);
                            k.setNaziv(naziv);
                            k.setAdresa(adresa);
                            k.setIdMesto(idmes);
                            
                            em.persist(k);
                        }
                    }
                    em.getTransaction().commit();
                    
                } finally {
                    if (em.getTransaction().isActive()) {
                        em.getTransaction().rollback();
                    }
                }
                
                em.clear();
                
                List<Filijala> listaFil = em.createNamedQuery("Filijala.findAll", Filijala.class).getResultList();

                try {
                    em.getTransaction().begin();
                    for (String fil : filijale) {
                        boolean ima = false;
                        StringTokenizer stringTokenizer = new StringTokenizer(fil, "?");
                        int idfil = Integer.parseInt(stringTokenizer.nextToken());
                        String naziv = stringTokenizer.nextToken();
                        String adresa = stringTokenizer.nextToken();
                        int idmes = Integer.parseInt(stringTokenizer.nextToken());
                        
                        for (Filijala f : listaFil) {
                            if (f.getIdFilijala()==idfil) {
                                ima = true;
                                break;
                            }
                        }

                        if (ima == false) {
                            Filijala f = new Filijala();
                            f.setIdFilijala(idfil);
                            f.setIdMesto(idmes);
                            f.setNaziv(naziv);
                            f.setAdresa(adresa);

                            em.persist(f);
                        }
                    }
                    em.getTransaction().commit();
                } finally {
                    if (em.getTransaction().isActive()) {
                        em.getTransaction().rollback();
                    }
                }

                em.clear();
                
                
                List<Racun> listaRac = em.createNamedQuery("Racun.findAll", Racun.class).getResultList();

                try {
                    em.getTransaction().begin();
                    for (String rac : racuni) {
                        boolean ima = false;
                        StringTokenizer stringTokenizer = new StringTokenizer(rac, "?");
                        
                        int idrac = Integer.parseInt(stringTokenizer.nextToken());
                        double stanje= Double.parseDouble(stringTokenizer.nextToken());
                        double minus= Double.parseDouble(stringTokenizer.nextToken());
                        String status = stringTokenizer.nextToken();
                        long datum= Long.parseLong(stringTokenizer.nextToken());
                        int br = Integer.parseInt(stringTokenizer.nextToken());
                        int idkom = Integer.parseInt(stringTokenizer.nextToken());
                        int idmes = Integer.parseInt(stringTokenizer.nextToken());
                        
                        for (Racun r : listaRac) {
                            if (r.getIdRacun() == idrac) {
                                ima = true;
                                if (r.getStanje().doubleValue() != stanje) {
                                    r.setStanje(new BigDecimal(stanje+""));
                                }
                                
                                if (!(r.getStatus().equals(status))) {
                                    r.setStatus(status);
                                }
                                
                                if(r.getBrojTransakcija()!=br){
                                    r.setBrojTransakcija(br);
                                }
                                
                                break;
                            }
                        }

                        if (ima == false) {
                            Racun r= new Racun();
                            r.setIdRacun(idrac);
                            r.setStanje(new BigDecimal(stanje+""));
                            r.setDozvoljeniMinus(new BigDecimal(minus+""));
                            r.setStatus(status);
                            r.setDatumOtvaranja(new Date(datum));
                            r.setBrojTransakcija(br);
                            r.setIdMesto(idmes);
                            r.setIdKomitent(idkom);
                           
                            em.persist(r);
                        }
                    }
                    em.getTransaction().commit();
                    
                } finally {
                    if (em.getTransaction().isActive()) {
                        em.getTransaction().rollback();
                    }
                }
                
                em.clear();
                
                
                List<Transakcija> listaTra = em.createNamedQuery("Transakcija.findAll", Transakcija.class).getResultList();

                try {
                    em.getTransaction().begin();
                    for (String tra : transakcije) {
                        boolean ima = false;
                        StringTokenizer stringTokenizer = new StringTokenizer(tra, "?");
                        
                        int idtra = Integer.parseInt(stringTokenizer.nextToken());
                        int idrac = Integer.parseInt(stringTokenizer.nextToken());
                        int rb = Integer.parseInt(stringTokenizer.nextToken());
                        long datum= Long.parseLong(stringTokenizer.nextToken());
                        double iznos= Double.parseDouble(stringTokenizer.nextToken());
                        String svrha = stringTokenizer.nextToken();
       
                        
                        for (Transakcija t : listaTra) {
                            if (t.getIdTransakcija() == idtra) {
                                ima = true;                             
                                break;
                            }
                        }

                        if (ima == false) {
                            Transakcija t= new Transakcija();
                            t.setIdTransakcija(idtra);
                            t.setIdRacun(idrac);
                            t.setRedniBroj(rb);
                            t.setDatumVreme(new Date(datum));
                            t.setIznos(new BigDecimal(iznos+""));
                            t.setSvrha(svrha);
                           
                            em.persist(t);
                        }
                    }
                    em.getTransaction().commit();
                    
                } finally {
                    if (em.getTransaction().isActive()) {
                        em.getTransaction().rollback();
                    }
                }
                
                em.clear();
                
                
                List<Uplata> listaUpl = em.createNamedQuery("Uplata.findAll", Uplata.class).getResultList();

                try {
                    em.getTransaction().begin();
                    for (String upl : uplate) {
                        boolean ima = false;
                        StringTokenizer stringTokenizer = new StringTokenizer(upl, "?");
                        
                        int idu = Integer.parseInt(stringTokenizer.nextToken());
                        int idfil = Integer.parseInt(stringTokenizer.nextToken());

                        for (Uplata u : listaUpl) {
                            if (u.getIdTransakcija()== idu) {
                                ima = true;
                                break;
                            }
                        }

                        if (ima == false) {
                            Uplata u = new Uplata();
                            u.setIdTransakcija(idu);
                            u.setIdFilijala(idfil);

                            em.persist(u);
                        }
                    }
                    em.getTransaction().commit();
                } finally {
                    if (em.getTransaction().isActive()) {
                        em.getTransaction().rollback();
                    }
                }

                em.clear();
                
                List<Isplata> listaIsp = em.createNamedQuery("Isplata.findAll", Isplata.class).getResultList();

                try {
                    em.getTransaction().begin();
                    for (String isp : isplate) {
                        boolean ima = false;
                        StringTokenizer stringTokenizer = new StringTokenizer(isp, "?");
                        
                        int idi = Integer.parseInt(stringTokenizer.nextToken());
                        int idfil = Integer.parseInt(stringTokenizer.nextToken());

                        for (Isplata i : listaIsp) {
                            if (i.getIdTransakcija()== idi) {
                                ima = true;
                                break;
                            }
                        }

                        if (ima == false) {
                            Isplata i = new Isplata();
                            i.setIdTransakcija(idi);
                            i.setIdFilijala(idfil);

                            em.persist(i);
                        }
                    }
                    em.getTransaction().commit();
                } finally {
                    if (em.getTransaction().isActive()) {
                        em.getTransaction().rollback();
                    }
                }

                em.clear();
                
                List<Prenos> listaPre = em.createNamedQuery("Prenos.findAll", Prenos.class).getResultList();

                try {
                    em.getTransaction().begin();
                    for (String pre : prenosi) {
                        boolean ima = false;
                        StringTokenizer stringTokenizer = new StringTokenizer(pre, "?");
                        
                        int idp = Integer.parseInt(stringTokenizer.nextToken());
                        int idrac = Integer.parseInt(stringTokenizer.nextToken());

                        for (Prenos p : listaPre) {
                            if (p.getIdTransakcija()== idp) {
                                ima = true;
                                break;
                            }
                        }

                        if (ima == false) {
                            Prenos p = new Prenos();
                            p.setIdTransakcija(idp);
                            p.setIdRacunSa(idrac);

                            em.persist(p);
                        }
                    }
                    em.getTransaction().commit();
                } finally {
                    if (em.getTransaction().isActive()) {
                        em.getTransaction().rollback();
                    }
                }

                em.clear();
                

                em.close();
                
                System.out.print("Kopiranje izvrseno");

                Thread.sleep(120000);

            } catch (JMSException ex) {
                Logger.getLogger(NitZaKopiranje.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(NitZaKopiranje.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
