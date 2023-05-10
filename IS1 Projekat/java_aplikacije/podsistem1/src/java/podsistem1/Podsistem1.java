/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podsistem1;

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
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Mico
 */
public class Podsistem1 {

    @Resource(lookup="projectFactory")
    public static ConnectionFactory connFactory;
    
    @Resource(lookup="QueueOdg")
    public static Queue queueOdg;
    
    @Resource(lookup="QueuePod1")
    public static Queue queue1;
    
    public static EntityManagerFactory emf= Persistence.createEntityManagerFactory("podsistem1PU");
   
    public static void main(String[] args) {
       // System.out.println("Krenuo");
        /*JMSContext context= connFactory.createContext();
       JMSConsumer consumer = context.createConsumer(queue1);
       JMSConsumer consumer2 = context.createConsumer(queueOdg);
      
       while(true){
       System.out.println("hheeeeeeejeeeeeeeeeeeeeee");
       consumer.receive();
       System.out.println("hheeeeeeeeeeeeeeeeeeeeee");}*/
       
       
      NitZahtev1 nit1 = new NitZahtev1();
        NitZahtev2 nit2 = new NitZahtev2();
        NitZahtev3 nit3 = new NitZahtev3();
        NitZahtev4 nit4 = new NitZahtev4();
        NitZahtev10 nit10 = new NitZahtev10();
        NitZahtev11 nit11 = new NitZahtev11();
        NitZahtev12 nit12 = new NitZahtev12();
        NitZahtev5 nit5 = new NitZahtev5();
        NitZahtev8 nit8 = new NitZahtev8();
        NitZahtev16 nit16 = new NitZahtev16();
        NitZaKopiranjePod1 nitpod1= new NitZaKopiranjePod1();
        
        nit1.start();
        nit2.start();
        nit3.start();
        nit4.start();
        nit5.start();
        nit8.start();
        nit10.start();
        nit11.start();
        nit12.start();
        nit16.start();
        nitpod1.start();
    }
    
}
