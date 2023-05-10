/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podsistem2;

import entiteti.Transakcija;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Mico
 */
public class Podsistem2 {

    @Resource(lookup="projectFactory")
    public static ConnectionFactory connFactory;
    
    @Resource(lookup="QueueOdg")
    public static Queue queueOdg;
    
    @Resource(lookup="QueuePod2")
    public static Queue queue2;
    
    public static EntityManagerFactory emf= Persistence.createEntityManagerFactory("podsistem2PU");
   
    public static void main(String[] args) {
       NitZahtev3 nit3= new NitZahtev3();
       NitZahtev4 nit4= new NitZahtev4();
       NitZahtev5 nit5= new NitZahtev5();
       NitZahtev6 nit6= new NitZahtev6();
       NitZahtev7 nit7= new NitZahtev7();
       NitZahtev8 nit8= new NitZahtev8();
       NitZahtev9 nit9= new NitZahtev9();
       NitZahtev13 nit13= new NitZahtev13();
       NitZahtev14 nit14= new NitZahtev14();
       NitZahtev16 nit16 = new NitZahtev16();
       NitZaKopiranjePod2 nitpod2= new NitZaKopiranjePod2();
       
       nit3.start();
       nit4.start();
       nit5.start();
       nit6.start();
       nit7.start();
       nit8.start();
       nit9.start();
       nit13.start();
       nit14.start();
       nit16.start();
       nitpod2.start();
    }
    
}
