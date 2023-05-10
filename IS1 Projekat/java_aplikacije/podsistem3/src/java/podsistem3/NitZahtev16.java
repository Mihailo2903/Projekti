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
public class NitZahtev16 extends Thread {

    @Override
    public void run() {
        JMSContext context = connFactory.createContext();
        JMSProducer producer = context.createProducer();
        JMSConsumer consumer1 = context.createConsumer(queueOdg, "Broj = 161");
        JMSConsumer consumer2 = context.createConsumer(queueOdg, "Broj = 162");

        while (true) {
            try {
                ObjectMessage poruka1 = (ObjectMessage) consumer1.receive();
                ObjectMessage poruka2 = (ObjectMessage) consumer2.receive();

                EntityManager em = emf.createEntityManager();
                StringBuilder builder= new StringBuilder();

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
                
                builder.append("Razlike:??");
                
                builder.append("Mesta:?");

                List<Mesto> listaMes = em.createNamedQuery("Mesto.findAll", Mesto.class).getResultList();
                                   
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
                        builder.append("Mesto sa identifikatorm IdMesto=" + idmes + " se ne nalazi u kopiji?");
                    }
                }
                   
                builder.append("####################?");

                
                builder.append("Komitenti:?");

                List<Komitent> listaKom = em.createNamedQuery("Komitent.findAll", Komitent.class).getResultList();
                
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
                               builder.append("Komitent sa identifikatorom IdKomitent=" + idkom + " u originalu ima vrednost IdMesto=" + idmes + " ,a u kopiji vrednost IdMesto= " + k.getIdMesto()+"?");          
                            }
                            break;
                        }
                    }

                    if (ima == false) {
                        builder.append("Komitent sa identifikatorm IdKomitent=" + idkom + " se ne nalazi u kopiji?");
                    }
                }
                         
                builder.append("####################?");
                
                builder.append("Filijale:?");                
                List<Filijala> listaFil = em.createNamedQuery("Filijala.findAll", Filijala.class).getResultList();

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
                        builder.append("Filijala sa identifikatorm IdFilijala=" + idfil + " se ne nalazi u kopiji?");
                    }
                }
               
                builder.append("####################?");
                
                builder.append("Racuni:?"); 
                
                List<Racun> listaRac = em.createNamedQuery("Racun.findAll", Racun.class).getResultList();

                
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
                                builder.append("Racun sa identifikatorom IdRacun=" + idrac + " u originalu ima vrednost Stanje=" + stanje + " ,a u kopiji vrednost Stanje= " + r.getStanje().doubleValue() +"?");
                            }

                            if (!(r.getStatus().equals(status))) {
                                builder.append("Racun sa identifikatorom IdRacun=" + idrac + " u originalu ima vrednost Status=" + status + " ,a u kopiji vrednost Status= " + r.getStatus() +"?");
                            }

                            if(r.getBrojTransakcija()!=br){
                                builder.append("Racun sa identifikatorom IdRacun=" + idrac + " u originalu ima vrednost BrojTransakcija=" + br + " ,a u kopiji vrednost BrojTransakcija= " + r.getBrojTransakcija() +"?");
                            }
                            break;
                        }
                    }

                    if (ima == false) {
                        builder.append("Racun sa identifikatorm IdRacun=" + idrac + " se ne nalazi u kopiji?");
                    }
                }
                  
                builder.append("####################?");    
                
                builder.append("Transakcije:?");               
                List<Transakcija> listaTra = em.createNamedQuery("Transakcija.findAll", Transakcija.class).getResultList();

           
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
                        builder.append("Transakcija sa identifikatorm IdTransakcija=" + idtra + " se ne nalazi u kopiji?");
                    }
                }
                builder.append("####################?");   
                
                builder.append("Uplate:?");
                List<Uplata> listaUpl = em.createNamedQuery("Uplata.findAll", Uplata.class).getResultList();

               
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
                        builder.append("Uplata sa identifikatorm IdTransakcija=" + idu + " se ne nalazi u kopiji?");
                    }
                }
                builder.append("####################?"); 
            
                builder.append("Isplate:?");
                List<Isplata> listaIsp = em.createNamedQuery("Isplata.findAll", Isplata.class).getResultList();

                
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
                       builder.append("Isplata sa identifikatorm IdTransakcija=" + idi + " se ne nalazi u kopiji?");
                    }
                }
                builder.append("####################?"); 
                
                builder.append("Prenosi:?");
                List<Prenos> listaPre = em.createNamedQuery("Prenos.findAll", Prenos.class).getResultList();

               
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
                        builder.append("Prenos sa identifikatorm IdTransakcija=" + idp + " se ne nalazi u kopiji?");
                    }
                }
                
                builder.append("####################?"); 
                    
                em.close();          
                
                ObjectMessage objOdg = context.createObjectMessage();
                objOdg.setObject(builder.toString());
                objOdg.setIntProperty("Broj", 16);

                producer.send(queueOdg, objOdg);


            } catch (JMSException ex) {
                Logger.getLogger(NitZahtev16.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }
    }

}
