/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endpoints;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 *
 * @author Mico
 */

@Path("zahtevi")
public class Zahtevi {
    
    @Resource(lookup="projectFactory")
    public  ConnectionFactory connFactory;
    
    @Resource(lookup="QueueOdg")
    public  Queue queueOdg;
    
    @Resource(lookup="QueuePod1")
    public  Queue queue1;
    
    @Resource(lookup="QueuePod2")
    public  Queue queue2;
    
    @Resource(lookup="QueuePod3")
    public  Queue queue3;
    
    @POST
    @Path("zahtev1/{broj}/{naziv}")
    public Response zahtev1(@PathParam("broj")int broj, @PathParam("naziv")String naziv1){
        JMSContext context= connFactory.createContext();
        JMSProducer producer= context.createProducer();
        JMSConsumer consumer= context.createConsumer(queueOdg,"Broj = 1");
        
        //System.out.println("Zahtev poslat");
        
        String naziv=naziv1.replace('_', ' ');
        
        try {        
            ObjectMessage msg=context.createObjectMessage();
            
            msg.setObject(broj);
            
            msg.setIntProperty("PosBroj", broj);
            msg.setStringProperty("Naziv", naziv);
            msg.setIntProperty("Broj", 1);
            
            producer.send(queue1, msg);
            
           // System.out.println("Poruka poslata");
                
        } catch (JMSException ex) {
            Logger.getLogger(Zahtevi.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Message m=consumer.receive();         
        ObjectMessage obj= (ObjectMessage)m;
        
        consumer.close();
        context.close();
        
        try {
            return Response.ok().entity(obj.getObject().toString()).build();
        } catch (JMSException ex) {
            Logger.getLogger(Zahtevi.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.ok().entity("Kreiranje nije uspelo").build();
    }
    
    
    @POST
    @Path("zahtev2/{naziv}/{adresa}/{mesto}")
    public Response zahtev2(@PathParam("naziv")String naziv1, @PathParam("adresa")String adresa1, @PathParam("mesto")String mesto1){
        JMSContext context= connFactory.createContext();
        JMSProducer producer= context.createProducer();
        JMSConsumer consumer= context.createConsumer(queueOdg,"Broj = 2");
        
        String naziv=naziv1.replace('_', ' ');
        String adresa=adresa1.replace('_', ' ');
        String mesto=mesto1.replace('_', ' ');
            
        try {        
            ObjectMessage msg=context.createObjectMessage();
                    
            msg.setStringProperty("Naziv", naziv);
            msg.setStringProperty("Adresa", adresa);
            msg.setStringProperty("Mesto", mesto);
            msg.setIntProperty("Broj", 2);
            
            producer.send(queue1, msg);
                         
        } catch (JMSException ex) {
            Logger.getLogger(Zahtevi.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Message m=consumer.receive();         
        ObjectMessage obj= (ObjectMessage)m;
        
        consumer.close();
        context.close();
        
        try {
            return Response.ok().entity(obj.getObject().toString()).build();
        } catch (JMSException ex) {
            Logger.getLogger(Zahtevi.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.ok().entity("Kreiranje nije uspelo").build();
    }
    
    @POST
    @Path("zahtev3/{naziv}/{adresa}/{mesto}")
    public Response zahtev3(@PathParam("naziv")String naziv1, @PathParam("adresa")String adresa1, @PathParam("mesto")String mesto1){
        JMSContext context= connFactory.createContext();
        JMSProducer producer= context.createProducer();
        JMSConsumer consumer= context.createConsumer(queueOdg,"Broj = 3");
        JMSConsumer consumer2= context.createConsumer(queueOdg, "Broj = 33");
        
        String naziv=naziv1.replace('_', ' ');
        String adresa=adresa1.replace('_', ' ');
        String mesto=mesto1.replace('_', ' ');
            
        try {        
            ObjectMessage msg=context.createObjectMessage();
                    
            msg.setStringProperty("Naziv", naziv);
            msg.setStringProperty("Adresa", adresa);
            msg.setStringProperty("Mesto", mesto);
            msg.setIntProperty("Broj", 3);
            
            producer.send(queue1, msg);
           
                         
        } catch (JMSException ex) {
            Logger.getLogger(Zahtevi.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Message m=consumer.receive();         
        ObjectMessage obj= (ObjectMessage)m;      
                       
        Message m2=consumer2.receive();
        ObjectMessage obj2= (ObjectMessage)m2; 
            
        int idmes;
       
        
        try {
            idmes=obj2.getIntProperty("IdMesto");
            if(idmes!=0){
                ObjectMessage msg2=context.createObjectMessage();
                msg2.setStringProperty("Naziv", naziv);
                msg2.setStringProperty("Adresa", adresa);
                msg2.setIntProperty("IdMesto", idmes);
                msg2.setIntProperty("Broj", 33);
                producer.send(queue2, msg2);
               
                consumer2.receive();
                
            }
        } catch (JMSException ex) {
            Logger.getLogger(Zahtevi.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        consumer.close();
        consumer2.close();       
        context.close();
        
        try {
            return Response.ok().entity(obj.getObject().toString()).build();
        } catch (JMSException ex) {
            Logger.getLogger(Zahtevi.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.ok().entity("Kreiranje nije uspelo").build();
    }
    
    @POST
    @Path("zahtev4/{naziv}/{mesto}")
    public Response zahtev4(@PathParam("naziv")String naziv1, @PathParam("mesto")String mesto1){
        JMSContext context= connFactory.createContext();
        JMSProducer producer= context.createProducer();
        JMSConsumer consumer= context.createConsumer(queueOdg,"Broj = 4");
        JMSConsumer consumer2= context.createConsumer(queueOdg, "Broj= 44");
        
        String naziv=naziv1.replace('_', ' ');
        String mesto=mesto1.replace('_', ' ');
            
        try {        
            ObjectMessage msg=context.createObjectMessage();
                    
            msg.setStringProperty("Naziv", naziv);
            msg.setStringProperty("Mesto", mesto);
            msg.setIntProperty("Broj", 4);
            
            producer.send(queue1, msg);           
                         
        } catch (JMSException ex) {
            Logger.getLogger(Zahtevi.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Message m=consumer.receive();         
        ObjectMessage obj= (ObjectMessage)m;
        
        Message m2=consumer2.receive();
        ObjectMessage obj2= (ObjectMessage)m2; 
            
        int idmes;
             
        try {
            idmes=obj2.getIntProperty("IdMesto");
            if(idmes!=0){
                ObjectMessage msg2=context.createObjectMessage();
                msg2.setStringProperty("Naziv", naziv);
                msg2.setIntProperty("IdMesto", idmes);
                msg2.setIntProperty("Broj", 44);
                producer.send(queue2, msg2);
               
                consumer2.receive();              
            }
        } catch (JMSException ex) {
            Logger.getLogger(Zahtevi.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        consumer.close();
        consumer2.close();
        context.close();
              
        try {
            return Response.ok().entity(obj.getObject().toString()).build();
        } catch (JMSException ex) {
            Logger.getLogger(Zahtevi.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.ok().entity("Promena nije uspela").build();
    }
    
    @GET
    @Path("zahtev10")
    public Response zahtev10(){
        JMSContext context= connFactory.createContext();
        JMSProducer producer= context.createProducer();
        JMSConsumer consumer= context.createConsumer(queueOdg,"Broj = 10");
              
        try {        
            ObjectMessage msg=context.createObjectMessage();
                    
            msg.setIntProperty("Broj", 10);
            
            producer.send(queue1, msg);
                         
        } catch (JMSException ex) {
            Logger.getLogger(Zahtevi.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Message m=consumer.receive();         
        ObjectMessage obj=(ObjectMessage)m;
        
        consumer.close();
        context.close();
              
        try {
            return Response.ok().entity(obj.getObject().toString()).build();
        } catch (JMSException ex) {
            Logger.getLogger(Zahtevi.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.ok().entity("Greska").build();
    }
    
    @GET
    @Path("zahtev11")
    public Response zahtev11(){
        JMSContext context= connFactory.createContext();
        JMSProducer producer= context.createProducer();
        JMSConsumer consumer= context.createConsumer(queueOdg,"Broj = 11");
              
        try {        
            ObjectMessage msg=context.createObjectMessage();
                    
            msg.setIntProperty("Broj", 11);
            
            producer.send(queue1, msg);
                         
        } catch (JMSException ex) {
            Logger.getLogger(Zahtevi.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Message m=consumer.receive();         
        ObjectMessage obj=(ObjectMessage)m;
        
        consumer.close();
        context.close();
              
        try {
            return Response.ok().entity(obj.getObject().toString()).build();
        } catch (JMSException ex) {
            Logger.getLogger(Zahtevi.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.ok().entity("Greska").build();
    }
    
     @GET
    @Path("zahtev12")
    public Response zahtev12(){
        JMSContext context= connFactory.createContext();
        JMSProducer producer= context.createProducer();
        JMSConsumer consumer= context.createConsumer(queueOdg,"Broj = 12");
              
        try {        
            ObjectMessage msg=context.createObjectMessage();
                    
            msg.setIntProperty("Broj", 12);
            
            producer.send(queue1, msg);
                         
        } catch (JMSException ex) {
            Logger.getLogger(Zahtevi.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Message m=consumer.receive();         
        ObjectMessage obj=(ObjectMessage)m;
        
        consumer.close();
        context.close();
              
        try {
            return Response.ok().entity(obj.getObject().toString()).build();
        } catch (JMSException ex) {
            Logger.getLogger(Zahtevi.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.ok().entity("Greska").build();
    }
    
    
    @POST
    @Path("zahtev5/{minus}/{naziv}/{mesto}")
    public Response zahtev5(@PathParam("minus")String minus1, @PathParam("naziv")String naziv1, @PathParam("mesto")String mesto1){
        JMSContext context= connFactory.createContext();
        JMSProducer producer= context.createProducer();
        JMSConsumer consumer= context.createConsumer(queueOdg, "Broj = 55");
        
        String naziv=naziv1.replace('_', ' ');
        String mesto=mesto1.replace('_', ' ');
        String minus=minus1.replace('_', ' ');
            
        try {        
            ObjectMessage msg=context.createObjectMessage();
                          
            msg.setStringProperty("Mesto", mesto);
            msg.setIntProperty("Broj", 55);
            
            producer.send(queue1, msg);
           
                         
        } catch (JMSException ex) {
            Logger.getLogger(Zahtevi.class.getName()).log(Level.SEVERE, null, ex);
        }
                               
        Message m=consumer.receive();
        ObjectMessage obj= (ObjectMessage)m;
        
        ObjectMessage poruka = obj;
            
        int idmes;
             
        try {
            idmes=obj.getIntProperty("IdMesto");
            if(idmes!=0){
                ObjectMessage msg2=context.createObjectMessage();
                msg2.setStringProperty("Naziv", naziv);
                msg2.setIntProperty("IdMesto", idmes);
                msg2.setStringProperty("Minus", minus);
                msg2.setIntProperty("Broj", 55);
                producer.send(queue2, msg2);
               
                poruka=(ObjectMessage)consumer.receive();
                
            }
            else{                            
                consumer.close();     
                context.close();
                return Response.ok().entity("Nema mesta sa tim imenom u bazi").build();
            }
            
        } catch (JMSException ex) {
            Logger.getLogger(Zahtevi.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        consumer.close();     
        context.close();
        try{
         return Response.ok().entity(poruka.getObject().toString()).build();
        } catch (Exception e){
            return Response.ok().entity("Nije uspelo otvaranje").build();
        }
    }
    
    @POST
    @Path("zahtev6/{broj}")
    public Response zahtev6(@PathParam("broj")int broj){
        JMSContext context= connFactory.createContext();
        JMSProducer producer= context.createProducer();
        JMSConsumer consumer= context.createConsumer(queueOdg, "Broj = 6");
                 
        try {        
            ObjectMessage msg=context.createObjectMessage();
                          
            msg.setIntProperty("IdRacun", broj);
            msg.setIntProperty("Broj", 6);
            
            producer.send(queue2, msg);
           
                         
        } catch (JMSException ex) {
            Logger.getLogger(Zahtevi.class.getName()).log(Level.SEVERE, null, ex);
        }
                               
        Message m=consumer.receive();
        ObjectMessage obj= (ObjectMessage)m;  
        
        consumer.close();     
        context.close();
        
        try{
         return Response.ok().entity(obj.getObject().toString()).build();
        } catch (Exception e){
            return Response.ok().entity("Nije uspelo zatvaranje racuna").build();
        }
    }
    
    @POST
    @Path("zahtev7/{racunNa}/{racunSa}/{iznos}/{svrha}")
    public Response zahtev7(@PathParam("racunNa")int racunNa,@PathParam("racunSa")int racunSa,@PathParam("iznos")String iznos1,@PathParam("svrha")String svrha1){
        JMSContext context= connFactory.createContext();
        JMSProducer producer= context.createProducer();
        JMSConsumer consumer= context.createConsumer(queueOdg, "Broj = 7");
        
        String iznos=iznos1.replace('_', ' ');
        String svrha=svrha1.replace('_', ' ');
                 
        try {        
            ObjectMessage msg=context.createObjectMessage();
                          
            msg.setIntProperty("IdRacunNa", racunNa);
            msg.setIntProperty("IdRacunSa", racunSa);
            msg.setStringProperty("Iznos", iznos);
            msg.setStringProperty("Svrha", svrha);
            
            msg.setIntProperty("Broj", 7);
            
            producer.send(queue2, msg);
           
                         
        } catch (JMSException ex) {
            Logger.getLogger(Zahtevi.class.getName()).log(Level.SEVERE, null, ex);
        }
         
        Message m=consumer.receive();
        ObjectMessage obj= (ObjectMessage)m;  
        
        consumer.close();     
        context.close();
        
        
        try{
            String s= obj.getObject().toString();
            return Response.ok().entity(s).build();
        } catch (Exception e){
           // System.out.println("HEHE3443");
            return Response.ok().entity("Nije uspeo prenos").build();
        }
    }

    @POST
    @Path("zahtev8/{broj}/{iznos}/{naziv}/{svrha}")
    public Response zahtev8(@PathParam("broj")int idRac, @PathParam("iznos")String iznos1, @PathParam("naziv")String naziv1, @PathParam("svrha")String svrha1){
        JMSContext context= connFactory.createContext();
        JMSProducer producer= context.createProducer();
        JMSConsumer consumer= context.createConsumer(queueOdg, "Broj = 88");
        
        String naziv=naziv1.replace('_', ' ');
        String iznos=iznos1.replace('_', ' ');
        String svrha=svrha1.replace('_', ' ');
            
        try {        
            ObjectMessage msg=context.createObjectMessage();
                          
            msg.setStringProperty("Filijala", naziv);
            msg.setIntProperty("Broj", 88);
            
            producer.send(queue1, msg);
            System.out.println("poslaooo");
                                   
        } catch (JMSException ex) {
            Logger.getLogger(Zahtevi.class.getName()).log(Level.SEVERE, null, ex);
        }
                               
        Message m=consumer.receive();
        ObjectMessage obj= (ObjectMessage)m;
        System.out.println("primio");
        
            
        int idfil;
             
        try {
            idfil=obj.getIntProperty("IdFilijala");
            if(idfil!=0){
                ObjectMessage msg2=context.createObjectMessage();
                msg2.setIntProperty("IdFilijala", idfil);
                msg2.setIntProperty("IdRacun", idRac);
                msg2.setStringProperty("Iznos", iznos);
                msg2.setStringProperty("Svrha", svrha);
                msg2.setIntProperty("Broj", 88);
                
                producer.send(queue2, msg2);
                System.out.println("poslaooo2");
                ObjectMessage poruka=(ObjectMessage)consumer.receive();
                System.out.println("primi02");
                consumer.close();     
                context.close();
                return Response.ok().entity(poruka.getObject().toString()).build();
            }
            else{                            
                consumer.close();     
                context.close();
                return Response.ok().entity("Nema filijale sa tim nazivom u bazi").build();
            }
            
        } catch (JMSException ex) {
            Logger.getLogger(Zahtevi.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return Response.ok().entity("Nije uspelo").build();
    }     
    
    
    @POST
    @Path("zahtev9/{broj}/{iznos}/{naziv}/{svrha}")
    public Response zahtev9(@PathParam("broj")int idRac, @PathParam("iznos")String iznos1, @PathParam("naziv")String naziv1, @PathParam("svrha")String svrha1){
        JMSContext context= connFactory.createContext();
        JMSProducer producer= context.createProducer();
        JMSConsumer consumer= context.createConsumer(queueOdg, "Broj = 88");
        JMSConsumer consumer2= context.createConsumer(queueOdg, "Broj = 99");
        
        String naziv=naziv1.replace('_', ' ');
        String iznos=iznos1.replace('_', ' ');
        String svrha=svrha1.replace('_', ' ');
            
        try {        
            ObjectMessage msg=context.createObjectMessage();
                          
            msg.setStringProperty("Filijala", naziv);
            msg.setIntProperty("Broj", 88);
            
            producer.send(queue1, msg);
                                   
        } catch (JMSException ex) {
            Logger.getLogger(Zahtevi.class.getName()).log(Level.SEVERE, null, ex);
        }
                               
        Message m=consumer.receive();
        ObjectMessage obj= (ObjectMessage)m;
        
        ObjectMessage poruka = null;
            
        int idfil;
             
        try {
            idfil=obj.getIntProperty("IdFilijala");
            if(idfil!=0){
                ObjectMessage msg2=context.createObjectMessage();
                msg2.setIntProperty("IdFilijala", idfil);
                msg2.setIntProperty("IdRacun", idRac);
                msg2.setStringProperty("Iznos", iznos);
                msg2.setStringProperty("Svrha", svrha);
                msg2.setIntProperty("Broj", 99);
                producer.send(queue2, msg2);
               
                poruka=(ObjectMessage)consumer2.receive();
                
            }
            else{                            
                consumer.close();     
                context.close();
                return Response.ok().entity("Nema filijale sa tim nazivom u bazi").build();
            }
            
        } catch (JMSException ex) {
            Logger.getLogger(Zahtevi.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        consumer2.close();
        consumer.close();     
        context.close();
        
        try{
         return Response.ok().entity(poruka.getObject().toString()).build();
        } catch (Exception e){
            return Response.ok().entity("Nije uspela isplata").build();
        }
    }

    @GET
    @Path("zahtev13/{naziv}")
    public Response zahtev13(@PathParam("naziv")int broj){
        JMSContext context= connFactory.createContext();
        JMSProducer producer= context.createProducer();
        JMSConsumer consumer= context.createConsumer(queueOdg, "Broj = 13");
        
                 
        try {        
            ObjectMessage msg=context.createObjectMessage();
                          
            msg.setIntProperty("IdKomitent", broj);
            msg.setIntProperty("Broj", 13);
            
            producer.send(queue2, msg);
           
                         
        } catch (JMSException ex) {
            Logger.getLogger(Zahtevi.class.getName()).log(Level.SEVERE, null, ex);
        }
                               
        Message m=consumer.receive();
        ObjectMessage obj= (ObjectMessage)m;  
      //  System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        consumer.close();     
        context.close();
        
        try{
         return Response.ok().entity(obj.getObject().toString()).build();
        } catch (Exception e){
            return Response.ok().entity("Nije uspela pretraga").build();
        }
    }
    
    @GET
    @Path("zahtev14/{broj}")
    public Response zahtev14(@PathParam("broj")int broj){
        JMSContext context= connFactory.createContext();
        JMSProducer producer= context.createProducer();
        JMSConsumer consumer= context.createConsumer(queueOdg, "Broj = 14");
                       
        try {        
            ObjectMessage msg=context.createObjectMessage();
                          
            msg.setIntProperty("IdRacun", broj);
            msg.setIntProperty("Broj", 14);
            
            producer.send(queue2, msg);
           
                         
        } catch (JMSException ex) {
            Logger.getLogger(Zahtevi.class.getName()).log(Level.SEVERE, null, ex);
        }
                               
        Message m=consumer.receive();
        ObjectMessage obj= (ObjectMessage)m;  
       
        consumer.close();     
        context.close();
        
        try{
         return Response.ok().entity(obj.getObject().toString()).build();
        } catch (Exception e){
            return Response.ok().entity("Nije uspela pretraga").build();
        }
    }
    
    @GET
    @Path("zahtev15")
    public Response zahtev15(){
        JMSContext context= connFactory.createContext();
        JMSProducer producer= context.createProducer();
        JMSConsumer consumer= context.createConsumer(queueOdg,"Broj = 15");
              
        try {        
            ObjectMessage msg=context.createObjectMessage();
                    
            msg.setIntProperty("Broj", 15);
            
            producer.send(queue3, msg);
                         
        } catch (JMSException ex) {
            Logger.getLogger(Zahtevi.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Message m=consumer.receive();         
        ObjectMessage obj=(ObjectMessage)m;
        
        consumer.close();
        context.close();
              
        try {
            return Response.ok().entity(obj.getObject().toString()).build();
        } catch (JMSException ex) {
            Logger.getLogger(Zahtevi.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.ok().entity("Greska").build();
    }
    
    @GET
    @Path("zahtev16")
    public Response zahtev16(){
        JMSContext context= connFactory.createContext();
        JMSProducer producer= context.createProducer();
        JMSConsumer consumer= context.createConsumer(queueOdg,"Broj = 16");
              
        try {        
            ObjectMessage msg1=context.createObjectMessage();
            ObjectMessage msg2=context.createObjectMessage();
                    
            msg1.setIntProperty("Broj", 16);
            msg2.setIntProperty("Broj", 16);
            
            producer.send(queue1, msg1);
            producer.send(queue2, msg2);
                         
        } catch (JMSException ex) {
            Logger.getLogger(Zahtevi.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Message m=consumer.receive();         
        ObjectMessage obj=(ObjectMessage)m;
        
        consumer.close();
        context.close();
              
        try {
            return Response.ok().entity(obj.getObject().toString()).build();
        } catch (JMSException ex) {
            Logger.getLogger(Zahtevi.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.ok().entity("Greska").build();
    }
}
