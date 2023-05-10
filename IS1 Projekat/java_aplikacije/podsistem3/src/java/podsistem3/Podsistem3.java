/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podsistem3;

import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Mico
 */
public class Podsistem3 {

    @Resource(lookup="projectFactory")
    public static ConnectionFactory connFactory;
    
    @Resource(lookup="QueueOdg")
    public static Queue queueOdg;
    
    @Resource(lookup="QueuePod2")
    public static Queue queue2;
    
    @Resource(lookup="QueuePod3")
    public static Queue queue3;
    
    @Resource(lookup="QueuePod1")
    public static Queue queue1;
    
    public static EntityManagerFactory emf= Persistence.createEntityManagerFactory("podsistem3PU");
    
    
    public static void main(String[] args) {
        
      /* JMSContext context= connFactory.createContext();
       JMSConsumer consumer1 = context.createConsumer(queue1);
       JMSConsumer consumer2 = context.createConsumer(queue2);
       JMSConsumer consumer3 = context.createConsumer(queue3);
       JMSConsumer consumer4 = context.createConsumer(queueOdg);
      
       while(true){
       System.out.println("hheeeeeeeeeeeeeeeeeeeeee");
       consumer4.receive();
       System.out.println("hheeeeeeeeeeeeeeeeeeeeee");} */
     
        NitZaKopiranje nitkop= new NitZaKopiranje();
        NitZahtev15 nit15= new NitZahtev15();
        NitZahtev16 nit16= new NitZahtev16();
        
        nit15.start();
        nit16.start();
        nitkop.start();
    }
    
}
