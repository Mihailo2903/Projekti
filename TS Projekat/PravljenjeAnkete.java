/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testovi;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;



public class PravljenjeAnkete {
    public static WebDriver driverEdge;
    public static String baseUrl = "http://localhost/projekat2/projekat_ankete/index.php";
    
    @BeforeSuite
    public static void PostaviDriver() {
        System.setProperty("webdriver.edge.driver", "C:\\Users\\korisnik\\Downloads\\edgedriver_win64\\msedgedriver.exe");
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\korisnik\\Downloads\\chromedriver\\chromedriver.exe");
    }
    
    public static void LogujSe() { //uloguje sa kao kreator i odabere opciju za pravljenje ankete
        driverEdge = new EdgeDriver();
        driverEdge.get(baseUrl);
        driverEdge.manage().window().maximize();

        driverEdge.findElement(By.name("username")).sendKeys("Filip");
        driverEdge.findElement(By.name("sifra")).sendKeys("sifra123");
        driverEdge.findElement(By.name("logovanje")).click();

        driverEdge.findElement(By.linkText("Napravi anketu")).click();
    }
    
    
    public static void VerifikacionaTackaSadrzi(String ocekivano, String dobijeno, String broj) {
        try {
            Assert.assertTrue(dobijeno.contains(ocekivano));
            System.out.println("TEST " + broj + " PROSAO");
        } catch (Throwable e) {
            System.out.println("TEST " + broj + " PAO");
        }
    }
    
    public static void popuniPrvuStranu(String naziv, String datumPoc, String datumKraj, int op, String brPitanja, String brStrana ){
        driverEdge.findElement(By.name("naziv")).sendKeys(naziv);
        driverEdge.findElement(By.name("pocetak")).sendKeys(datumPoc);
        driverEdge.findElement(By.name("kraj")).sendKeys(datumKraj);
        WebElement vrsta= driverEdge.findElement(By.name("pers"));
        if(op==0)
            vrsta.sendKeys("0");
        else
            vrsta.sendKeys("1");
        driverEdge.findElement(By.name("brojpitanja")).sendKeys(brPitanja);
        driverEdge.findElement(By.name("brojstrana")).sendKeys(brStrana);
        
        driverEdge.findElement(By.name("dalje")).click();     
    }
    
    public static void popuniDruguStranu(String p1, String p2, String p3, String p4, String p5, String p6, String p7, String p8){
        driverEdge.findElement(By.name("pit1")).sendKeys(p1);
        driverEdge.findElement(By.name("pit2")).sendKeys(p2);
        driverEdge.findElement(By.name("pit3")).sendKeys(p3);
        driverEdge.findElement(By.name("pit4")).sendKeys(p4);
        driverEdge.findElement(By.name("pit5")).sendKeys(p5);
        driverEdge.findElement(By.name("pit6")).sendKeys(p6);
        driverEdge.findElement(By.name("pit7")).sendKeys(p7);
        driverEdge.findElement(By.name("pit8")).sendKeys(p8);
        
        driverEdge.findElement(By.name("dalje")).click(); 

    }
    
 
    @Test
    public static void test46() {
        LogujSe();
        popuniPrvuStranu("anketica1","10222021","12222021",1,"2","1");
        popuniDruguStranu("0","0","0","1","0","0","0","1");
        
        //trecaStrana
        driverEdge.findElement(By.name("brpolja40")).sendKeys("1");
        driverEdge.findElement(By.name("dalje")).click(); 
        
        //cetvrtaStrana
        driverEdge.findElement(By.name("tekst40")).sendKeys("Tekst1");
        driverEdge.findElement(By.name("odgovor400")).sendKeys("Odg1");
        driverEdge.findElement(By.name("dodatno40")).sendKeys("dodatno");
        driverEdge.findElement(By.name("tip41")).sendKeys("1");
        driverEdge.findElement(By.name("obavezno41")).sendKeys("1");
        driverEdge.findElement(By.name("obavezno81")).sendKeys("0");
        
        Select pitanje = new Select(driverEdge.findElement(By.name("tekst80")));
        pitanje.selectByVisibleText("Omiljeni praznici su mi:");
      
        driverEdge.findElement(By.name("kraj")).submit();
        
        String welcomeMsg = driverEdge.findElement(By.xpath("//*[contains(text(),'Pravljenje')]")).getText();
        
        VerifikacionaTackaSadrzi("ankete", welcomeMsg, "46");
        driverEdge.findElement(By.linkText("Izloguj se")).click();
        driverEdge.close();
        
    }
    
    @Test
    public static void test47(){
        LogujSe();
        popuniPrvuStranu("anketica2","10222021","10222021",0,"2","2");
        popuniDruguStranu("1","1","0","0","0","0","0","0");
        
        //trecaStrana
        driverEdge.findElement(By.name("brpolja10")).sendKeys("1");
        driverEdge.findElement(By.name("brpolja20")).sendKeys("1");
        driverEdge.findElement(By.name("dalje")).submit();
        
        //cetvrtaStrana
        driverEdge.findElement(By.name("tekst10")).sendKeys("Tekst1");
        driverEdge.findElement(By.name("polje100")).sendKeys("Odg1");
        driverEdge.findElement(By.name("obavezno11")).sendKeys("1");
        
        driverEdge.findElement(By.name("sledeca")).submit();
        
        //petaStrana
        driverEdge.findElement(By.name("tekst20")).sendKeys("Tekst2");
        driverEdge.findElement(By.name("polje200")).sendKeys("11");
        driverEdge.findElement(By.name("obavezno21")).sendKeys("0");
        
        driverEdge.findElement(By.name("kraj")).submit();
        String welcomeMsg = driverEdge.findElement(By.xpath("//*[contains(text(),'Pravljenje')]")).getText();
        
        VerifikacionaTackaSadrzi("ankete", welcomeMsg, "47");
        driverEdge.findElement(By.linkText("Izloguj se")).click();
        driverEdge.close();
        
    }


    @Test
    public static void test48(){
        LogujSe();
        popuniPrvuStranu("","10222021","12222021",1,"2","1");
        
        String s = "";
        try {
            s = driverEdge.switchTo().alert().getText();
            driverEdge.switchTo().alert().dismiss();
        } catch (Throwable t) {
            s = "";
        }
        try {
            Assert.assertTrue(s.contains("uneli"));
            System.out.println("Test 48" + " prosao");
        } catch (Exception e) {

        } finally {
            if(!s.contains("uneli")){ 
                System.out.println("TEST 48 " + " PAO");
            }
            driverEdge.findElement(By.linkText("Izloguj se")).click();
            driverEdge.close();
        }
    
    }

    
    @Test
    public static void test49(){
        LogujSe();
        popuniPrvuStranu("SI ETF anketa","10222021","12222021",1,"2","1");
        String welcomeMsg = driverEdge.findElement(By.xpath("//*[contains(text(),'kreirana')]")).getText();
        
        VerifikacionaTackaSadrzi("kreirana", welcomeMsg, "49");
        driverEdge.findElement(By.linkText("Izloguj se")).click();
        driverEdge.close();
    
    }

    @Test
    public static void test50(){
        LogujSe();
        popuniPrvuStranu("anketaaaw","","12222021",1,"2","1");
            
        String s = "";
        try {
            s = driverEdge.switchTo().alert().getText();
            driverEdge.switchTo().alert().dismiss();
        } catch (Throwable t) {
            s = "";
        }
        try {
            Assert.assertTrue(s.contains("uneli"));
            System.out.println("Test 50" + " prosao");
        } catch (Exception e) {

        } finally {
            if(!s.contains("uneli")){ 
                System.out.println("TEST 50 " + " PAO");
            }
            driverEdge.findElement(By.linkText("Izloguj se")).click();
            driverEdge.close();
        }
    }

    @Test
    public static void test51(){
        LogujSe();
        popuniPrvuStranu("aa","10222021","",1,"2","1");
            
        String s = "";
        try {
            s = driverEdge.switchTo().alert().getText();
            driverEdge.switchTo().alert().dismiss();
        } catch (Throwable t) {
            s = "";
        }
        try {
            Assert.assertTrue(s.contains("uneli"));
            System.out.println("Test 51" + " prosao");
        } catch (Exception e) {

        } finally {
            if(!s.contains("uneli")){ 
                System.out.println("TEST 51 " + " PAO");
            }
            driverEdge.findElement(By.linkText("Izloguj se")).click();
            driverEdge.close();
        }
    }

    @Test
    public static void test52(){
        LogujSe();
        popuniPrvuStranu("aaak","10222021","09222021",1,"2","1");
            
        String s = "";
        try {
            s = driverEdge.switchTo().alert().getText();
            driverEdge.switchTo().alert().dismiss();
        } catch (Throwable t) {
            s = "";
        }
        try {
            Assert.assertTrue(s.contains("pre pocetka"));
            System.out.println("Test 52" + " prosao");
        } catch (Exception e) {

        } finally {
            if(!s.contains("pre pocetka")){ 
                System.out.println("TEST 52 " + " PAO");
            }
            driverEdge.findElement(By.linkText("Izloguj se")).click();
            driverEdge.close();
        }
    }
    
     @Test
    public static void test53(){
        LogujSe();
        popuniPrvuStranu("hhh","10222021","12222021",1,"A","1");
        String welcomeMsg = driverEdge.findElement(By.xpath("//*[contains(text(),'Greska')]")).getText();
        
        VerifikacionaTackaSadrzi("Greska", welcomeMsg, "53");
       
        driverEdge.close();
    
    }
    
    @Test
    public static void test54(){
        LogujSe();
        popuniPrvuStranu("hsshh","10222021","12222021",1,"0","1");
       
        String s = driverEdge.switchTo().alert().getText();
        driverEdge.switchTo().alert().dismiss();
        VerifikacionaTackaSadrzi("uneli", s, "54");
        driverEdge.findElement(By.linkText("Izloguj se")).click();
        driverEdge.close();
       
    }
    
     @Test
    public static void test55(){
        LogujSe();
        popuniPrvuStranu("hsssehh","10222021","12222021",1,"2","A");
       
        String s = driverEdge.switchTo().alert().getText();
        driverEdge.switchTo().alert().dismiss();
        VerifikacionaTackaSadrzi("izabrati", s, "55");
        driverEdge.findElement(By.linkText("Izloguj se")).click();
        driverEdge.close();
       
    }
    
     @Test
    public static void test56(){
        LogujSe();
        popuniPrvuStranu("hsaxashh","10222021","12222021",1,"2","0");
       
        String s = driverEdge.switchTo().alert().getText();
        driverEdge.switchTo().alert().dismiss();
        VerifikacionaTackaSadrzi("uneli", s, "56");
        driverEdge.findElement(By.linkText("Izloguj se")).click();
        driverEdge.close();
       
    }

     @Test
    public static void test57(){
        LogujSe();
        popuniPrvuStranu("hsaaschsh","10222021","12222021",1,"2","3");
       
        String s = driverEdge.switchTo().alert().getText();
        driverEdge.switchTo().alert().dismiss();
        VerifikacionaTackaSadrzi("stranica", s, "57");
        driverEdge.findElement(By.linkText("Izloguj se")).click();
        driverEdge.close();
       
    }
     @Test
    public static void test58(){
        LogujSe();
        popuniPrvuStranu("hsaaasqhh","10222021","12222021",1,"5","2");
       
        String s = driverEdge.switchTo().alert().getText();
        driverEdge.switchTo().alert().dismiss();
        VerifikacionaTackaSadrzi("stranica", s, "58");
        driverEdge.findElement(By.linkText("Izloguj se")).click();
        driverEdge.close();    
    }
    
    @Test
    public static void test59(){
        LogujSe();
        popuniPrvuStranu("anketica224","10222021","10222021",1,"2","1");
        popuniDruguStranu("","","0","0","0","0","0","0");
        
        String s = driverEdge.switchTo().alert().getText();
        driverEdge.switchTo().alert().dismiss();
        
        VerifikacionaTackaSadrzi("popuniti", s, "59");
        driverEdge.findElement(By.linkText("Izloguj se")).click();
        driverEdge.close();     
    }
    
    @Test
    public static void test60(){
        LogujSe();
        popuniPrvuStranu("anketica2223","10222021","10222021",1,"2","1");
        popuniDruguStranu("1","A","0","0","0","0","0","0");
        
        String s = driverEdge.switchTo().alert().getText();
        driverEdge.switchTo().alert().dismiss();
        
        VerifikacionaTackaSadrzi("Ukupan broj", s, "60");
        driverEdge.findElement(By.linkText("Izloguj se")).click();
        driverEdge.close();     
    }
    
    
    @Test
    public static void test61(){
        LogujSe();
        popuniPrvuStranu("anketica2222","10222021","10222021",1,"2","1");
        popuniDruguStranu("1","1","1","0","0","0","0","0");
        
        String s = driverEdge.switchTo().alert().getText();
        driverEdge.switchTo().alert().dismiss();
        
        VerifikacionaTackaSadrzi("Ukupan broj", s, "61");
        driverEdge.findElement(By.linkText("Izloguj se")).click();
        driverEdge.close();     
    }
    
    @Test
    public static void test62(){
        LogujSe();
        popuniPrvuStranu("anketica2122","10222021","10222021",1,"2","1");
        popuniDruguStranu("1","1","0","0","0","0","0","0");
        
        driverEdge.findElement(By.name("brpolja10")).sendKeys("1");
        driverEdge.findElement(By.name("brpolja20")).sendKeys("");
        
        String s = "";
        try {
            s = driverEdge.switchTo().alert().getText();
            driverEdge.switchTo().alert().dismiss();
        } catch (Throwable t) {
            s = "";
        }
        try {
            Assert.assertTrue(s.contains("uneli"));
            System.out.println("Test 62" + " prosao");
        } catch (Exception e) {

        } finally {
            if(!s.contains("uneli")){ 
                System.out.println("TEST 62 " + " PAO");
            }
            driverEdge.findElement(By.linkText("Izloguj se")).click();
            driverEdge.close();
        }     
    }
    
    
    @Test
    public static void test63(){
        LogujSe();
        popuniPrvuStranu("anketica22422","10222021","10222021",1,"2","1");
        popuniDruguStranu("1","1","0","0","0","0","0","0");
        
        driverEdge.findElement(By.name("brpolja10")).sendKeys("1");
        driverEdge.findElement(By.name("brpolja20")).sendKeys("A");
        
        String s = "";
        try {
            s = driverEdge.switchTo().alert().getText();
            driverEdge.switchTo().alert().dismiss();
        } catch (Throwable t) {
            s = "";
        }
        try {
            Assert.assertTrue(s.contains("uneli"));
            System.out.println("Test 63" + " prosao");
        } catch (Exception e) {

        } finally {
            if(!s.contains("uneli")){ 
                System.out.println("TEST 63 " + " PAO");
            }
            driverEdge.findElement(By.linkText("Izloguj se")).click();
            driverEdge.close();
        }     
    }
    
    @Test
    public static void test64(){
        LogujSe();
        popuniPrvuStranu("anketica224765222","10222021","10222021",1,"2","1");
        popuniDruguStranu("0","0","0","1","0","0","0","1");
        
        driverEdge.findElement(By.name("brpolja40")).sendKeys("1");
        driverEdge.findElement(By.name("dalje")).submit();
        
        driverEdge.findElement(By.name("tekst40")).sendKeys("");
        driverEdge.findElement(By.name("odgovor400")).sendKeys("22");
        driverEdge.findElement(By.name("dodatno40")).sendKeys("ss2");
        driverEdge.findElement(By.name("tip41")).sendKeys("1");
        driverEdge.findElement(By.name("obavezno41")).sendKeys("1");
        driverEdge.findElement(By.name("obavezno81")).sendKeys("1");
        
        Select pitanje = new Select(driverEdge.findElement(By.name("tekst80")));
        pitanje.selectByVisibleText("Omiljeni praznici su mi:");
      
        driverEdge.findElement(By.name("kraj")).submit();
        
       String s = "";
        try {
            s = driverEdge.switchTo().alert().getText();
            driverEdge.switchTo().alert().dismiss();
        } catch (Throwable t) {
            s = "";
        }
        try {
            Assert.assertTrue(s.contains("uneli"));
            System.out.println("Test 64" + " prosao");
        } catch (Exception e) {

        } finally {
            if(!s.contains("uneli")){ 
                System.out.println("TEST 64 " + " PAO");
            }
            driverEdge.findElement(By.linkText("Izloguj se")).click();
            driverEdge.close();
        }     
    }
    
     @Test
    public static void test65(){
        LogujSe();
        popuniPrvuStranu("anketica2243211222","10222021","10222021",1,"2","1");
        popuniDruguStranu("0","0","0","1","0","0","0","1");
        
        driverEdge.findElement(By.name("brpolja40")).sendKeys("1");
        driverEdge.findElement(By.name("dalje")).submit();
        
        driverEdge.findElement(By.name("tekst40")).sendKeys("te");
        driverEdge.findElement(By.name("odgovor400")).sendKeys("22");
        driverEdge.findElement(By.name("dodatno40")).sendKeys("ss2");
        driverEdge.findElement(By.name("tip41")).sendKeys("");
        driverEdge.findElement(By.name("obavezno41")).sendKeys("1");
        driverEdge.findElement(By.name("obavezno81")).sendKeys("1");
        
        Select pitanje = new Select(driverEdge.findElement(By.name("tekst80")));
        pitanje.selectByVisibleText("Omiljeni praznici su mi:");
      
        driverEdge.findElement(By.name("kraj")).submit();
        
       String s = "";
        try {
            s = driverEdge.switchTo().alert().getText();
            driverEdge.switchTo().alert().dismiss();
        } catch (Throwable t) {
            s = "";
        }
        try {
            Assert.assertTrue(s.contains("uneli"));
            System.out.println("Test 65" + " prosao");
        } catch (Exception e) {

        } finally {
            if(!s.contains("uneli")){ 
                System.out.println("TEST 65 " + " PAO");
            }
            driverEdge.findElement(By.linkText("Izloguj se")).click();
            driverEdge.close();
        }     
    }
    
     @Test
    public static void test66(){
        LogujSe();
        popuniPrvuStranu("anketica224979222","10222021","10222021",1,"2","1");
        popuniDruguStranu("0","0","0","1","0","0","0","1");
        
        driverEdge.findElement(By.name("brpolja40")).sendKeys("1");
        driverEdge.findElement(By.name("dalje")).submit();
        
        driverEdge.findElement(By.name("tekst40")).sendKeys("te");
        driverEdge.findElement(By.name("odgovor400")).sendKeys("22");
        driverEdge.findElement(By.name("dodatno40")).sendKeys("ss2");
        driverEdge.findElement(By.name("tip41")).sendKeys("1");
        driverEdge.findElement(By.name("obavezno41")).sendKeys("");
        driverEdge.findElement(By.name("obavezno81")).sendKeys("1");
        
        Select pitanje = new Select(driverEdge.findElement(By.name("tekst80")));
        pitanje.selectByVisibleText("Omiljeni praznici su mi:");
      
        driverEdge.findElement(By.name("kraj")).submit();
        
       String s = "";
        try {
            s = driverEdge.switchTo().alert().getText();
            driverEdge.switchTo().alert().dismiss();
        } catch (Throwable t) {
            s = "";
        }
        try {
            Assert.assertTrue(s.contains("uneli"));
            System.out.println("Test 66" + " prosao");
        } catch (Exception e) {

        } finally {
            if(!s.contains("uneli")){ 
                System.out.println("TEST 66 " + " PAO");
            }
            driverEdge.findElement(By.linkText("Izloguj se")).click();
            driverEdge.close();
        }     
    }
    
     @Test
    public static void test67(){
        LogujSe();
        popuniPrvuStranu("anketica2w2427822","10222021","10222021",1,"2","1");
        popuniDruguStranu("0","0","0","1","0","0","0","1");
        
        driverEdge.findElement(By.name("brpolja40")).sendKeys("1");
        driverEdge.findElement(By.name("dalje")).submit();
        
        driverEdge.findElement(By.name("tekst40")).sendKeys("te");
        driverEdge.findElement(By.name("odgovor400")).sendKeys("22");
        driverEdge.findElement(By.name("dodatno40")).sendKeys("ss2");
        driverEdge.findElement(By.name("tip41")).sendKeys("3");
        driverEdge.findElement(By.name("obavezno41")).sendKeys("1");
        driverEdge.findElement(By.name("obavezno81")).sendKeys("1");
        
        Select pitanje = new Select(driverEdge.findElement(By.name("tekst80")));
        pitanje.selectByVisibleText("Omiljeni praznici su mi:");
      
        driverEdge.findElement(By.name("kraj")).submit();
        
       String s = "";
        try {
            s = driverEdge.switchTo().alert().getText();
            driverEdge.switchTo().alert().dismiss();
        } catch (Throwable t) {
            s = "";
        }
        try {
            Assert.assertTrue(s.contains("pogresan"));
            System.out.println("Test 67" + " prosao");
        } catch (Exception e) {

        } finally {
            if(!s.contains("pogresan")){ 
                System.out.println("TEST 67 " + " PAO");
            }
            driverEdge.findElement(By.linkText("Izloguj se")).click();
            driverEdge.close();
        }     
    }
    
     @Test
    public static void test68(){
        LogujSe();
        popuniPrvuStranu("anketica2242e2672","10222021","10222021",1,"2","1");
        popuniDruguStranu("0","0","0","1","0","0","0","1");
        
        driverEdge.findElement(By.name("brpolja40")).sendKeys("1");
        driverEdge.findElement(By.name("dalje")).submit();
        
        driverEdge.findElement(By.name("tekst40")).sendKeys("te");
        driverEdge.findElement(By.name("odgovor400")).sendKeys("22");
        driverEdge.findElement(By.name("dodatno40")).sendKeys("ss2");
        driverEdge.findElement(By.name("tip41")).sendKeys("1");
        driverEdge.findElement(By.name("obavezno41")).sendKeys("3");
        driverEdge.findElement(By.name("obavezno81")).sendKeys("1");
        
        Select pitanje = new Select(driverEdge.findElement(By.name("tekst80")));
        pitanje.selectByVisibleText("Omiljeni praznici su mi:");
      
        driverEdge.findElement(By.name("kraj")).submit();
        
       String s = "";
        try {
            s = driverEdge.switchTo().alert().getText();
            driverEdge.switchTo().alert().dismiss();
        } catch (Throwable t) {
            s = "";
        }
        try {
            Assert.assertTrue(s.contains("uneli"));
            System.out.println("Test 68" + " prosao");
        } catch (Exception e) {

        } finally {
            if(!s.contains("uneli")){ 
                System.out.println("TEST 68 " + " PAO");
            }
            driverEdge.findElement(By.linkText("Izloguj se")).click();
            driverEdge.close();
        }     
    }
    
     @Test
    public static void test69(){
        LogujSe();
        popuniPrvuStranu("anketica223124f222","10222021","10222021",1,"2","1");
        popuniDruguStranu("0","0","0","1","0","0","0","1");
        
        driverEdge.findElement(By.name("brpolja40")).sendKeys("1");
        driverEdge.findElement(By.name("dalje")).submit();
        
        driverEdge.findElement(By.name("tekst40")).sendKeys("te");
        driverEdge.findElement(By.name("odgovor400")).sendKeys("22");
        driverEdge.findElement(By.name("dodatno40")).sendKeys("ss2");
        driverEdge.findElement(By.name("tip41")).sendKeys("1");
        driverEdge.findElement(By.name("obavezno41")).sendKeys("1");
        driverEdge.findElement(By.name("obavezno81")).sendKeys("1");
        
        driverEdge.findElement(By.name("kraj")).submit();
        
        String s = "";
        try {
            s = driverEdge.switchTo().alert().getText();
            driverEdge.switchTo().alert().dismiss();
        } catch (Throwable t) {
            s = "";
        }
        
        
        try {
            Assert.assertTrue(s.contains("uneli"));
            System.out.println("Test 69" + " prosao");
        } catch (Exception e) {

        } finally {
            if(!s.contains("uneli")){ 
                System.out.println("TEST 69 " + " PAO");
            }
            driverEdge.findElement(By.linkText("Izloguj se")).click();
            driverEdge.close();
        }     
    }
}