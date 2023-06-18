package rs.etf.sab.student;

import java.util.logging.Level;
import java.util.logging.Logger;
import rs.etf.sab.operations.*;
//import org.junit.Test;
import rs.etf.sab.tests.TestHandler;
import rs.etf.sab.tests.TestRunner;


public class StudentMain {
    
 
    public static void main(String[] args) {

        try {
            ArticleOperations articleOperations = new tm190185_ArticleOperations(); // Change this for your implementation (points will be negative if interfaces are not implemented).
            BuyerOperations buyerOperations = new tm190185_BuyerOperations();
            CityOperations cityOperations = new tm190185_CityOperations();
            GeneralOperations generalOperations = new tm190185_GeneralOperations();
            OrderOperations orderOperations = new tm190185_OrderOperations();
            ShopOperations shopOperations = new tm190185_ShopOperations();
            TransactionOperations transactionOperations = new tm190185_TransactionOperations();
            
            
            TestHandler.createInstance(
            articleOperations,
            buyerOperations,
            cityOperations,
            generalOperations,
            orderOperations,
            shopOperations,
            transactionOperations
            );
            
            TestRunner.runTests();
            
           /* PublicTest pt = new PublicTest();
            pt.setUp();
            pt.test();
            pt.tearDown();*/
        } catch (Exception ex) {
            Logger.getLogger(StudentMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
