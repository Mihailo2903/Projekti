/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podsistem2;


import entiteti.Isplata;
import entiteti.Komitent;
import entiteti.Prenos;
import entiteti.Racun;
import entiteti.Transakcija;
import entiteti.Uplata;
import java.util.ArrayList;
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
import static podsistem2.Podsistem2.emf;
import static podsistem2.Podsistem2.queue2;
import static podsistem2.Podsistem2.queueOdg;

public class NitZahtev16 extends Thread {

    @Override
    public void run() {
        JMSContext context = connFactory.createContext();
        JMSProducer producer = context.createProducer();
        JMSConsumer consumer = context.createConsumer(queue2,"Broj = 16");
        
        while (true) {
            try {
                ObjectMessage obj = (ObjectMessage) consumer.receive();

                EntityManager em = emf.createEntityManager();
                                           
                ArrayList<ArrayList<String>> ListaKonacno = new ArrayList<>();
                ArrayList<String> ListaRacuna = new ArrayList<>();
                ArrayList<String> ListaTransakcija = new ArrayList<>();
                ArrayList<String> ListaUplata = new ArrayList<>();
                ArrayList<String> ListaIsplata = new ArrayList<>();
                ArrayList<String> ListaPrenosa = new ArrayList<>();
                
                List<Racun> listaRac = em.createNamedQuery("Racun.findAll", Racun.class).getResultList();
                             
                for(Racun r: listaRac){
                    int id=r.getIdRacun();
                    double stanje=r.getStanje().doubleValue();
                    double minus=r.getDozvoljeniMinus().doubleValue();
                    String status=r.getStatus();
                    long d=r.getDatumOtvaranja().getTime();
                    int br=r.getBrojTransakcija();
                    int idkom=r.getIdKomitent().getIdKomitent();
                    int idmes=r.getIdMesto();

                    String racun= id+"?"+stanje+"?"+minus+"?"+status+"?"+d+"?"+br+"?"+idkom+"?"+idmes;
                    ListaRacuna.add(racun);
                }
                
                ListaKonacno.add(ListaRacuna);
                
                List<Transakcija> listaTra = em.createNamedQuery("Transakcija.findAll", Transakcija.class).getResultList();
                
                for(Transakcija t: listaTra){
                    int id=t.getIdTransakcija();
                    int idrac=t.getIdRacun().getIdRacun();
                    int rb=t.getRedniBroj();
                    long d=t.getDatumVreme().getTime();
                    double iznos=t.getIznos().doubleValue();
                    String svrha=t.getSvrha();

                    String tran= id+"?"+idrac+"?"+rb+"?"+d+"?"+iznos+"?"+svrha;
                    ListaTransakcija.add(tran);
                }
                
                ListaKonacno.add(ListaTransakcija);
                
                
                List<Uplata> listaUpl = em.createNamedQuery("Uplata.findAll", Uplata.class).getResultList();
                
                for(Uplata u: listaUpl){
                    int id=u.getIdTransakcija();
                    int idfil=u.getIdFilijala();
                    
                    String uplata= id+"?"+idfil;
                    ListaUplata.add(uplata);
                }
                
                ListaKonacno.add(ListaUplata);
                
                List<Isplata> listaIsp = em.createNamedQuery("Isplata.findAll", Isplata.class).getResultList();
                
                for(Isplata i: listaIsp){
                    int id=i.getIdTransakcija();
                    int idfil=i.getIdFilijala();
                    
                    String isplata= id+"?"+idfil;
                    ListaIsplata.add(isplata);
                }
                
                ListaKonacno.add(ListaIsplata);
                
                List<Prenos> listaPre = em.createNamedQuery("Prenos.findAll", Prenos.class).getResultList();
                
                for(Prenos p: listaPre){
                    int id=p.getIdTransakcija();
                    int idrac=p.getIdRacunSa().getIdRacun();
                    
                    String prenos= id+"?"+idrac;
                    ListaPrenosa.add(prenos);
                }
                
                ListaKonacno.add(ListaPrenosa);
                              
                em.close();
                
                ObjectMessage objOdg=context.createObjectMessage();
                objOdg.setObject(ListaKonacno);
                objOdg.setIntProperty("Broj", 162);
                
                producer.send(queueOdg, objOdg);

            } catch (JMSException ex) {
                Logger.getLogger(NitZahtev16.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
