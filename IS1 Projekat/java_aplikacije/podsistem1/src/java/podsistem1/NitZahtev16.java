/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podsistem1;

import entiteti.Filijala;
import entiteti.Komitent;
import entiteti.Mesto;
import java.util.ArrayList;
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
import static podsistem1.Podsistem1.emf;
import static podsistem1.Podsistem1.queue1;
import static podsistem1.Podsistem1.queueOdg;

public class NitZahtev16 extends Thread {

    @Override
    public void run() {
        JMSContext context = connFactory.createContext();
        JMSProducer producer = context.createProducer();
        JMSConsumer consumer = context.createConsumer(queue1,"Broj = 16");
        
        while (true) {
            try {
                ObjectMessage obj = (ObjectMessage) consumer.receive();

                EntityManager em = emf.createEntityManager();
                                           
                ArrayList<ArrayList<String>> ListaKonacno = new ArrayList<>();
                ArrayList<String> ListaMesta = new ArrayList<>();
                ArrayList<String> ListaKomitenata = new ArrayList<>();
                ArrayList<String> ListaFilijala = new ArrayList<>();
                
                List<Mesto> listaMes = em.createNamedQuery("Mesto.findAll", Mesto.class).getResultList();
                             
                for(Mesto m: listaMes){
                    int id=m.getIdMesto();
                    String naziv=m.getNaziv();
                    int broj=m.getPostanskiBroj();

                    String mesto= id+"?"+naziv+"?"+broj;
                    ListaMesta.add(mesto);
                }
                
                ListaKonacno.add(ListaMesta);
                
                List<Komitent> listaKom = em.createNamedQuery("Komitent.findAll", Komitent.class).getResultList();
                
                for(Komitent k: listaKom){
                    int id=k.getIdKomitent();
                    String naziv=k.getNaziv();
                    String adresa=k.getAdresa();
                    int idm=k.getIdMesto().getIdMesto();

                    String komitent= id+"?"+naziv+"?"+adresa+"?"+idm;
                    ListaKomitenata.add(komitent);
                }
                
                ListaKonacno.add(ListaKomitenata);
                
                
                List<Filijala> listaFil = em.createNamedQuery("Filijala.findAll", Filijala.class).getResultList();
                
                for(Filijala f: listaFil){
                    int id=f.getIdFilijala();
                    String naziv=f.getNaziv();
                    String adresa=f.getAdresa();
                    int idm=f.getIdMesto().getIdMesto();

                    String filijala= id+"?"+naziv+"?"+adresa+"?"+idm;
                    ListaFilijala.add(filijala);
                }
                
                ListaKonacno.add(ListaFilijala);
                
                em.close();
                
                ObjectMessage objOdg=context.createObjectMessage();
                objOdg.setObject(ListaKonacno);
                objOdg.setIntProperty("Broj", 161);
                
                producer.send(queueOdg, objOdg);

            } catch (JMSException ex) {
                Logger.getLogger(NitZahtev16.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
