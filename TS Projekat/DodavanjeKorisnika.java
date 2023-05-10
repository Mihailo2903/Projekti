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
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 *
 * @author korisnik
 */
public class DodavanjeKorisnika {

    public static WebDriver driverChrome;
    public static String baseUrl = "http://localhost/projekat2/projekat_ankete/index.php";

    @BeforeSuite
    public static void PostaviDriver() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\korisnik\\Downloads\\chromedriver\\chromedriver.exe");
    }

    /* @BeforeTest
    public static void LogujSe() {
        driverChrome = new ChromeDriver();
        driverChrome.get(baseUrl);
        driverChrome.manage().window().maximize();

        driverChrome.findElement(By.name("username")).sendKeys("Djordje");
        driverChrome.findElement(By.name("sifra")).sendKeys("sifra123");
        driverChrome.findElement(By.name("logovanje")).click();

        driverChrome.findElement(By.linkText("Dodaj novog korisnika")).click();
    }*/
    public static void LogujSe() {
        driverChrome = new ChromeDriver();
        driverChrome.get(baseUrl);
        driverChrome.manage().window().maximize();

        driverChrome.findElement(By.name("username")).sendKeys("Djordje");
        driverChrome.findElement(By.name("sifra")).sendKeys("sifra123");
        driverChrome.findElement(By.name("logovanje")).click();

        driverChrome.findElement(By.linkText("Dodaj novog korisnika")).click();
    }

    public static void popuni(String nick, String lozinka, String potvrda, String ime, String prezime, String datum, String mesto,
            String JMBG, String telefon, String email, char vrsta) {
        driverChrome.findElement(By.name("korisnicko_ime")).sendKeys(nick);
        driverChrome.findElement(By.name("sifra")).sendKeys(lozinka);
        driverChrome.findElement(By.name("potvrda_sifra")).sendKeys(potvrda);
        driverChrome.findElement(By.name("ime")).sendKeys(ime);
        driverChrome.findElement(By.name("prezime")).sendKeys(prezime);
        driverChrome.findElement(By.name("mesto_rodjenja")).sendKeys(mesto);
        driverChrome.findElement(By.name("jmbg")).sendKeys(JMBG);
        driverChrome.findElement(By.name("kon_telefon")).sendKeys(telefon);
        driverChrome.findElement(By.name("email")).sendKeys(email);

        Select vr = new Select(driverChrome.findElement(By.name("tip")));

        switch (vrsta) {
            case 'A':
                vr.selectByVisibleText("Administrator");
                break;
            case 'I':
                vr.selectByVisibleText("Ispitanik");
                break;
            case 'S':
                vr.selectByVisibleText("Službenik");
                break;
            case 'K':
                vr.selectByVisibleText("Kreator(autor) ankete");
                break;
            default:
                vr.selectByVisibleText("Izaberite tip naloga...");
                break;
        }
        driverChrome.findElement(By.name("datum_rodjenja")).sendKeys(datum);

        driverChrome.findElement(By.name("reg")).click();
    }

    public static void VerifikacionaTackaJednako(String ocekivano, String dobijeno, String broj) {
        try {
            Assert.assertEquals(dobijeno, dobijeno);
        } catch (Throwable e) {
            System.out.println("TEST " + broj + " PAO");
        }
    }

    public static void VerifikacionaTackaSadrzi(String ocekivano, String dobijeno, String broj) {
        try {
            Assert.assertTrue(dobijeno.contains(ocekivano));
            System.out.println("TEST " + broj + " PROSAO");
        } catch (Throwable e) {
            System.out.println("TEST " + broj + " PAO");
        }
    }

    @Test
    public static void test16a() {
        LogujSe();
        popuni("", "aaaaa", "aaaaa", "Mihailo", "Tomasevic", "01012000", "Hehe", "1234567891234", "+064123456", "abab@kk.com", 'K');
        String s = driverChrome.switchTo().alert().getText();
        driverChrome.switchTo().alert().dismiss();
        //System.out.println(s);
        VerifikacionaTackaSadrzi("popunili sva polja", s, "16a");
        driverChrome.findElement(By.linkText("Izloguj se")).click();
        driverChrome.close();
    }

    @Test
    public static void test16b() {
        LogujSe();
        popuni("Djordje", "aaaaa", "aaaaa", "Mihailo", "Tomasevic", "01012000", "Hehe", "1234567891234", "+064123456", "abab@kk.com", 'K');
        String welcomeMsg = driverChrome.findElement(By.xpath("//*[contains(text(),'postoji')]")).getText();
        VerifikacionaTackaSadrzi("postoji", welcomeMsg, "16b");
        driverChrome.findElement(By.linkText("Izloguj se")).click();
        driverChrome.close();
    }

    @Test
    public static void test17() {
        LogujSe();
        popuni("comi1", "aaa", "aaa", "Mihailo", "Tomasevic", "01012000", "Hehe", "1234567891234", "+064123456", "abab@kk.com", 'K');
        String s = driverChrome.switchTo().alert().getText();
        driverChrome.switchTo().alert().dismiss();
        VerifikacionaTackaSadrzi("prekratka", s, "17");
        driverChrome.findElement(By.linkText("Izloguj se")).click();
        driverChrome.close();
    }

    @Test
    public static void test18() {
        LogujSe();
        popuni("comi2", "aaaa", "aaaa", "Mihailo", "Tomasevic", "01012000", "Hehe", "1234567891234", "+064123456", "abab@kk.com", 'K');
        String s = driverChrome.switchTo().alert().getText();
        driverChrome.switchTo().alert().dismiss();
        VerifikacionaTackaSadrzi("prekratka", s, "18");
        driverChrome.findElement(By.linkText("Izloguj se")).click();
        driverChrome.close();
    }

    @Test
    public static void test19() {
        LogujSe();
        popuni("comi3", "aaaaa", "bbbbb", "Mihailo", "Tomasevic", "01012000", "Hehe", "1234567891234", "+064123456", "abab@kk.com", 'K');
        String s = driverChrome.switchTo().alert().getText();
        driverChrome.switchTo().alert().dismiss();
        VerifikacionaTackaSadrzi("ne poklapaju", s, "19");
        driverChrome.findElement(By.linkText("Izloguj se")).click();
        driverChrome.close();
    }

    @Test
    public static void test20() {
        LogujSe();
        popuni("comi4", "aaaaa", "aaaaa", "", "Tomasevic", "01012000", "Hehe", "1234567891234", "+064123456", "abab@kk.com", 'K');
        String s = driverChrome.switchTo().alert().getText();
        driverChrome.switchTo().alert().dismiss();
        VerifikacionaTackaSadrzi("sva polja", s, "20");
        driverChrome.findElement(By.linkText("Izloguj se")).click();
        driverChrome.close();
    }

    @Test
    public static void test21() {
        LogujSe();
        popuni("comi5", "aaaaa", "aaaaa", "D", "Tomasevic", "01012000", "Hehe", "1234567891234", "+064123456", "abab@kk.com", 'K');
        String s = driverChrome.switchTo().alert().getText();
        driverChrome.switchTo().alert().dismiss();
        VerifikacionaTackaSadrzi("unos imena", s, "21");
        driverChrome.findElement(By.linkText("Izloguj se")).click();
        driverChrome.close();
    }

    @Test
    public static void test23() {
        LogujSe();
        popuni("comi6", "aaaaa", "aaaaa", "daa", "Tomasevic", "01012000", "Hehe", "1234567891234", "+064123456", "abab@kk.com", 'K');
        String s = driverChrome.switchTo().alert().getText();
        driverChrome.switchTo().alert().dismiss();
        VerifikacionaTackaSadrzi("unos imena", s, "23");
        driverChrome.findElement(By.linkText("Izloguj se")).click();
        driverChrome.close();
    }

    @Test
    public static void test22() {
        LogujSe();
        popuni("comi7", "aaaaa", "aaaaa", "D22", "Tomasevic", "01012000", "Hehe", "1234567891234", "+064123456", "abab@kk.com", 'K');
        String s = driverChrome.switchTo().alert().getText();
        driverChrome.switchTo().alert().dismiss();
        VerifikacionaTackaSadrzi("unos imena", s, "22");
        driverChrome.findElement(By.linkText("Izloguj se")).click();
        driverChrome.close();
    }

    @Test
    public static void test24() {
        LogujSe();
        popuni("comi8", "aaaaa", "aaaaa", "AaD", "Tomasevic", "01012000", "Hehe", "1234567891234", "+064123456", "abab@kk.com", 'K');
        String s = driverChrome.switchTo().alert().getText();
        driverChrome.switchTo().alert().dismiss();
        VerifikacionaTackaSadrzi("unos imena", s, "24");
        driverChrome.findElement(By.linkText("Izloguj se")).click();
        driverChrome.close();
    }

    @Test
    public static void test25() {
        LogujSe();
        popuni("comi9", "aaaaa", "aaaaa", "Mihailo", "", "01012000", "Hehe", "1234567891234", "+064123456", "abab@kk.com", 'K');
        String s = driverChrome.switchTo().alert().getText();
        driverChrome.switchTo().alert().dismiss();
        VerifikacionaTackaSadrzi("sva polja", s, "25");
        driverChrome.findElement(By.linkText("Izloguj se")).click();
        driverChrome.close();
    }

    @Test
    public static void test26() {
        LogujSe();
        popuni("comi11", "aaaaa", "aaaaa", "Mihailo", "D", "01012000", "Hehe", "1234567891234", "+064123456", "abab@kk.com", 'K');
        String s = driverChrome.switchTo().alert().getText();
        driverChrome.switchTo().alert().dismiss();
        VerifikacionaTackaSadrzi("unos prezimena", s, "26");
        driverChrome.findElement(By.linkText("Izloguj se")).click();
        driverChrome.close();
    }

    @Test
    public static void test27() {
        LogujSe();
        popuni("comi12", "aaaaa", "aaaaa", "Mihailo", "D22", "01012000", "Hehe", "1234567891234", "+064123456", "abab@kk.com", 'K');
        String s = driverChrome.switchTo().alert().getText();
        driverChrome.switchTo().alert().dismiss();
        VerifikacionaTackaSadrzi("unos prezimena", s, "27");
        driverChrome.findElement(By.linkText("Izloguj se")).click();
        driverChrome.close();
    }

    @Test
    public static void test28() {
        LogujSe();
        popuni("comi13", "aaaaa", "aaaaa", "Mihailo", "daa", "01012000", "Hehe", "1234567891234", "+064123456", "abab@kk.com", 'K');
        String s = driverChrome.switchTo().alert().getText();
        driverChrome.switchTo().alert().dismiss();
        VerifikacionaTackaSadrzi("unos prezimena", s, "28");
        driverChrome.findElement(By.linkText("Izloguj se")).click();
        driverChrome.close();
    }

    @Test
    public static void test29() {
        LogujSe();
        popuni("comi14", "aaaaa", "aaaaa", "Mihailo", "AaD", "01012000", "Hehe", "1234567891234", "+064123456", "abab@kk.com", 'K');
        String s = driverChrome.switchTo().alert().getText();
        driverChrome.switchTo().alert().dismiss();
        VerifikacionaTackaSadrzi("unos prezimena", s, "29");
        driverChrome.findElement(By.linkText("Izloguj se")).click();
        driverChrome.close();
    }

    @Test
    public static void test30() throws Throwable {
        LogujSe();
        popuni("comi2212f2", "aaaaa", "aaaaa", "Mihailo", "Tomasevic", "", "Hehe", "1234567891234", "+064123456", "abab@kk.com", 'K');
        String s = "";
        try {
            s = driverChrome.switchTo().alert().getText();
            driverChrome.switchTo().alert().dismiss();
        } catch (Throwable t) {
            s = "";
        }
        try {
            Assert.assertTrue(s.contains("sva polja"));
            System.out.println("Test 30" + " prosao");
        } catch (Exception e) {

        } finally {
            if(!s.contains("sva polja")){ 
                System.out.println("TEST 30 " + " PAO");
            }
            driverChrome.findElement(By.linkText("Izloguj se")).click();
            driverChrome.close();
        }
    }

    @Test
    public static void test31() throws Throwable {
        LogujSe();
        popuni("comi22232", "aaaaa", "aaaaa", "Mihailo", "Tomasevic", "29122022", "Hehe", "1234567891234", "+064123456", "abab@kk.com", 'K');
        String s = "";
        try {
            s = driverChrome.switchTo().alert().getText();
            driverChrome.switchTo().alert().dismiss();
        } catch (Throwable t) {
            s = "";
        }
        try {
            Assert.assertTrue(s.contains("neispravan datum"));
            System.out.println("TEST 31" + " PROSAO");
        } catch (Exception e) {

        } finally {
            if(!s.contains("neispravan datum")){
                 System.out.println("TEST 31 " + " PAO");
            }
            driverChrome.findElement(By.linkText("Izloguj se")).click();
            driverChrome.close();
        }
    }

    @Test
    public static void test32() {
        LogujSe();
        popuni("comi323", "aaaaa", "aaaaa", "Mihailo", "Tomasevic", "01012000", "", "1234567891234", "+064123456", "abab@kk.com", 'K');
        String s = driverChrome.switchTo().alert().getText();
        driverChrome.switchTo().alert().dismiss();
        VerifikacionaTackaSadrzi("sva polja", s, "32");
        driverChrome.findElement(By.linkText("Izloguj se")).click();
        driverChrome.close();
    }

    @Test
    public static void test33() {
        LogujSe();
        popuni("comi34", "aaaaa", "aaaaa", "Mihailo", "Tomasevic", "01012000", "D", "1234567891234", "+064123456", "abab@kk.com", 'K');
        String s = driverChrome.switchTo().alert().getText();
        driverChrome.switchTo().alert().dismiss();
        VerifikacionaTackaSadrzi("unos mesta", s, "33");
        driverChrome.findElement(By.linkText("Izloguj se")).click();
        driverChrome.close();
    }

    @Test
    public static void test34() {
        LogujSe();
        popuni("comi552", "aaaaa", "aaaaa", "Mihailo", "Tomasevic", "01012000", "D22", "1234567891234", "+064123456", "abab@kk.com", 'K');
        String s = driverChrome.switchTo().alert().getText();
        driverChrome.switchTo().alert().dismiss();
        VerifikacionaTackaSadrzi("unos mesta", s, "34");
        driverChrome.findElement(By.linkText("Izloguj se")).click();
        driverChrome.close();
    }

    @Test
    public static void test35() {
        LogujSe();
        popuni("comi5522", "aaaaa", "aaaaa", "Mihailo", "Tomasevic", "01012000", "daa", "1234567891234", "+064123456", "abab@kk.com", 'K');
        String s = driverChrome.switchTo().alert().getText();
        driverChrome.switchTo().alert().dismiss();
        VerifikacionaTackaSadrzi("unos mesta", s, "35");
        driverChrome.findElement(By.linkText("Izloguj se")).click();
        driverChrome.close();
    }

    @Test
    public static void test36() {
        LogujSe();
        popuni("comi77", "aaaaa", "aaaaa", "Mihailo", "Tomasevic", "01012000", "AaD", "1234567891234", "+064123456", "abab@kk.com", 'K');
        String s = driverChrome.switchTo().alert().getText();
        driverChrome.switchTo().alert().dismiss();
        VerifikacionaTackaSadrzi("unos mesta", s, "36");
        driverChrome.findElement(By.linkText("Izloguj se")).click();
        driverChrome.close();
    }

    @Test
    public static void test37() {
        LogujSe();
        popuni("comi57", "aaaaa", "aaaaa", "Mihailo", "Tomasevic", "01012000", "Hehe", "12345678901", "+064123456", "abab@kk.com", 'K');
        String s = driverChrome.switchTo().alert().getText();
        driverChrome.switchTo().alert().dismiss();
        VerifikacionaTackaSadrzi("13 cifara", s, "37");
        driverChrome.findElement(By.linkText("Izloguj se")).click();
        driverChrome.close();
    }

    @Test
    public static void test38() {
        LogujSe();
        popuni("comi99", "aaaaa", "aaaaa", "Mihailo", "Tomasevic", "01012000", "Hehe", "123456789012", "+064123456", "abab@kk.com", 'K');
        String s = driverChrome.switchTo().alert().getText();
        driverChrome.switchTo().alert().dismiss();
        VerifikacionaTackaSadrzi("13 cifara", s, "38");
        driverChrome.findElement(By.linkText("Izloguj se")).click();
        driverChrome.close();
    }

    @Test
    public static void test39() {
        LogujSe();
        popuni("comi653", "aaaaa", "aaaaa", "Mihailo", "Tomasevic", "01012000", "Hehe", "12345678901234", "+064123456", "abab@kk.com", 'K');
        String s = driverChrome.switchTo().alert().getText();
        driverChrome.switchTo().alert().dismiss();
        VerifikacionaTackaSadrzi("13 cifara", s, "39");
        driverChrome.findElement(By.linkText("Izloguj se")).click();
        driverChrome.close();
    }

    @Test
    public static void test40() {
        LogujSe();
        popuni("comi543", "aaaaa", "aaaaa", "Mihailo", "Tomasevic", "01012000", "Hehe", "123456789012345", "+064123456", "abab@kk.com", 'K');
        String s = driverChrome.switchTo().alert().getText();
        driverChrome.switchTo().alert().dismiss();
        VerifikacionaTackaSadrzi("13 cifara", s, "40");
        driverChrome.findElement(By.linkText("Izloguj se")).click();
        driverChrome.close();
    }

    @Test
    public static void test41() {
        LogujSe();
        popuni("comi2211", "aaaaa", "aaaaa", "Mihailo", "Tomasevic", "01012000", "Hehe", "123456789123s", "+064123456", "abab@kk.com", 'K');
        String s = driverChrome.switchTo().alert().getText();
        driverChrome.switchTo().alert().dismiss();
        VerifikacionaTackaSadrzi("13 cifara", s, "41");
        driverChrome.findElement(By.linkText("Izloguj se")).click();
        driverChrome.close();
    }

    @Test
    public static void test42() throws Throwable {
        LogujSe();
        popuni("comi2222132", "aaaaa", "aaaaa", "Mihailo", "Tomasevic", "01012000", "Hehe", "1234567891234", "aa", "abab@kk.com", 'K');
        String s = "";
        try {
            s = driverChrome.switchTo().alert().getText();
            driverChrome.switchTo().alert().dismiss();
        } catch (Throwable t) {
            s = "";
        }
        try {
            Assert.assertTrue(s.contains("telefon"));
            System.out.println("TEST 42" + " PROSAO");
        } catch (Exception e) {

        } finally {
            if(!s.contains("telefon")){
            System.out.println("TEST 42 " + " PAO");
            }
            driverChrome.findElement(By.linkText("Izloguj se")).click();
            driverChrome.close();
        }
    }

    @Test
    public static void test43() {
        LogujSe();
        popuni("comi675", "aaaaa", "aaaaa", "Mihailo", "Tomasevic", "01012000", "Hehe", "1234567890123", "", "abab@kk.com", 'K');
        String s = driverChrome.switchTo().alert().getText();
        driverChrome.switchTo().alert().dismiss();
        VerifikacionaTackaSadrzi("sva polja", s, "43");
        driverChrome.findElement(By.linkText("Izloguj se")).click();
        driverChrome.close();
    }

    @Test
    public static void test44() {
        LogujSe();
        popuni("comi875", "aaaaa", "aaaaa", "Mihailo", "Tomasevic", "01012000", "Hehe", "1234567890123", "343535335", "aa", 'K');
        String s = driverChrome.switchTo().alert().getText();
        driverChrome.switchTo().alert().dismiss();
        VerifikacionaTackaSadrzi("email", s, "44");
        driverChrome.findElement(By.linkText("Izloguj se")).click();
        driverChrome.close();
    }

    @Test
    public static void test45() {
        LogujSe();
        popuni("comi2310", "aaaaa", "aaaaa", "Mihailo", "Tomasevic", "01012000", "Hehe", "1234567890123", "43434", "abab@kk.com", 'P');
        String s = driverChrome.switchTo().alert().getText();
        driverChrome.switchTo().alert().dismiss();
        VerifikacionaTackaSadrzi("sva polja", s, "45");
        driverChrome.findElement(By.linkText("Izloguj se")).click();
        driverChrome.close();
    }

}
