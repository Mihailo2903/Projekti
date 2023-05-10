/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klijent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import static java.lang.System.in;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mico
 */
public class Klijent {

    public static Scanner scan = new Scanner(in);

    public static void ispisiMeni() {
        System.out.println("Odaberite opciju:");
        System.out.println("1. Kreiraj novo mesto");
        System.out.println("2. Kreiraj novu filijalu");
        System.out.println("3. Kreiraj novog komitenta");
        System.out.println("4. Promeni sediste komitentu");
        System.out.println("5. Otvori novi racun");
        System.out.println("6. Zatvori racun");
        System.out.println("7. Izvrsi prenos sa racuna na racun"); 
        System.out.println("8. Izvrsi uplatu na racun"); 
        System.out.println("9. Izvrsi isplatu sa racuna"); 
        System.out.println("10. Dohvati podatke o mestima");
        System.out.println("11. Dohvati podatke o filijalama");
        System.out.println("12. Dohvati podatke o komitentima");
        System.out.println("13. Dohvati sve racune komitenta");
        System.out.println("14. Dohvati sve transakcije racuna");
        System.out.println("15. Dohvati sve podatke iz rezervne kopije ");
        System.out.println("16. Dohvati sve razlike između originala i kopije ");
        System.out.println("----------------------");
        System.out.println();
    }

    public static void main(String[] args) {
            ispisiMeni();
            String linija;
            int brojOpcije;

            while (true) {
                linija=scan.nextLine();
                brojOpcije = Integer.parseInt(linija);
                boolean kraj=false;

                switch (brojOpcije) {
                    case 1: zahtev1(); break;
                    case 2: zahtev2(); break;
                    case 3: zahtev3(); break;
                    case 4: zahtev4(); break;
                    case 5: zahtev5(); break;
                    case 6: zahtev6(); break;
                    case 7: zahtev7(); break;
                    case 8: zahtev8(); break;
                    case 9: zahtev9(); break;
                    case 10: zahtev10(); break;
                    case 11: zahtev11(); break;
                    case 12: zahtev12(); break;
                    case 13: zahtev13(); break;
                    case 14: zahtev14(); break;
                    case 15: zahtev15(); break;
                    case 16: zahtev16(); break;
                    default: kraj=true; break;
                }
                if(kraj==true)
                    break;
                
                System.out.println();
                ispisiMeni();

            }                 
    }

    private static void zahtev1() {
        try {
            System.out.print("Unesite naziv mesta: ");
            String naziv1 = scan.nextLine();
            System.out.print("Unesite postanski broj mesta: ");
            String br = scan.nextLine();
            int broj=Integer.parseInt(br);
            
            String naziv=naziv1.replace(' ', '_');

            URL url = new URL("http://localhost:8080/server/resources/zahtevi/zahtev1/" + broj + "/" + naziv);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            

            System.out.println(content.toString());
            
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void zahtev2() {
        try {
            System.out.print("Unesite naziv filijale: ");
            String naziv1 = scan.nextLine();
            System.out.print("Unesite adresu filijale: ");
            String adresa1 = scan.nextLine();
            System.out.print("Unesite naziv mesta : ");
            String mesto1 = scan.nextLine();
            
            String naziv=naziv1.replace(' ', '_');
            String adresa=adresa1.replace(' ', '_');
            String mesto=mesto1.replace(' ', '_');
            
            URL url = new URL("http://localhost:8080/server/resources/zahtevi/zahtev2/" + naziv + "/" + adresa + "/" + mesto);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            

            System.out.println(content.toString());
            
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ProtocolException ex) {
            Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    private static void zahtev3() {
        try {
            System.out.print("Unesite naziv komitenta: ");
            String naziv1 = scan.nextLine();
            System.out.print("Unesite adresu komitenta: ");
            String adresa1 = scan.nextLine();
            System.out.print("Unesite naziv mesta koje je sediste : ");
            String mesto1 = scan.nextLine();
            
            String naziv=naziv1.replace(' ', '_');
            String adresa=adresa1.replace(' ', '_');
            String mesto=mesto1.replace(' ', '_');
            
            URL url = new URL("http://localhost:8080/server/resources/zahtevi/zahtev3/" + naziv + "/" + adresa + "/" + mesto);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            System.out.println(content.toString());
              
        } catch (MalformedURLException ex) {
            Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ProtocolException ex) {
            Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void zahtev4() {
       try {
            System.out.print("Unesite naziv komitenta: ");
            String naziv1 = scan.nextLine();
            System.out.print("Unesite naziv novog sedista komitenta: ");
            String mesto1 = scan.nextLine();
                     
            String naziv=naziv1.replace(' ', '_');
            String mesto=mesto1.replace(' ', '_');
              
            URL url = new URL("http://localhost:8080/server/resources/zahtevi/zahtev4/" + naziv + "/" + mesto);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            String s=content.toString().replace('?', '\n');
            System.out.println(s);
              
        } catch (MalformedURLException ex) {
            Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ProtocolException ex) {
            Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void zahtev10() {
        try {
            System.out.println();
              
            URL url = new URL("http://localhost:8080/server/resources/zahtevi/zahtev10");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            
            in.close();
            String s=content.toString().replace('?', '\n');
            System.out.println(s);
              
        } catch (MalformedURLException ex) {
            Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ProtocolException ex) {
            Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void zahtev11() {
        try {
            System.out.println();
              
            URL url = new URL("http://localhost:8080/server/resources/zahtevi/zahtev11");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            
            in.close();
            String s=content.toString().replace('?', '\n');
            System.out.println(s);
              
        } catch (MalformedURLException ex) {
            Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ProtocolException ex) {
            Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void zahtev12() {
       try {
            System.out.println();
              
            URL url = new URL("http://localhost:8080/server/resources/zahtevi/zahtev12");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            
            in.close();
            String s=content.toString().replace('?', '\n');
            System.out.println(s);
              
        } catch (MalformedURLException ex) {
            Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ProtocolException ex) {
            Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void zahtev5() {
        try {
            System.out.print("Unesite dozvoljeni minus: ");
            String minus1 = scan.nextLine();
            System.out.print("Unesite naziv vlasnika: ");
            String naziv1 = scan.nextLine();
            System.out.print("Unesite naziv mesta u kome se racun otvara: ");
            String mesto1 = scan.nextLine();
            
            String naziv=naziv1.replace(' ', '_');
            String mesto=mesto1.replace(' ', '_');
            String minus=minus1.replace(' ', '_');
            
            URL url = new URL("http://localhost:8080/server/resources/zahtevi/zahtev5/" + minus + "/" + naziv + "/" + mesto);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            System.out.println(content.toString());
              
        } catch (MalformedURLException ex) {
            Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ProtocolException ex) {
            Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void zahtev6() {
        try {
            System.out.print("Unesite broj racuna koji zelite da zatvorite: ");
            String broj1 = scan.nextLine();
            int broj=Integer.parseInt(broj1);
            
            URL url = new URL("http://localhost:8080/server/resources/zahtevi/zahtev6/" + broj);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            System.out.println(content.toString());
              
        } catch (MalformedURLException ex) {
            Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ProtocolException ex) {
            Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void zahtev7() {
        try {
            System.out.print("Unesite broj racuna na koji vrsite prenos: ");
            String broj1 = scan.nextLine();
            int broj=Integer.parseInt(broj1);
            System.out.print("Unesite broj racuna sa kog vrsite prenos: ");
            String broj2 = scan.nextLine();
            int brojj=Integer.parseInt(broj2);
            System.out.print("Unesite iznos koji se prenosi: ");
            String broj3 = scan.nextLine();
            System.out.print("Unesite svrhu prenosa: ");
            String svrha1 = scan.nextLine();
            
            String brojjj=broj3.replace(' ', '_');
            String svrha= svrha1.replace(' ', '_');
            
            URL url = new URL("http://localhost:8080/server/resources/zahtevi/zahtev7/" + broj +"/" + brojj +"/" + brojjj + "/" + svrha);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            System.out.println(content.toString());
              
        } catch (MalformedURLException ex) {
            Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ProtocolException ex) {
            Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void zahtev8() {
       try {
            System.out.print("Unesite broj racuna na koji vrsite uplatu: ");
            String broj1 = scan.nextLine();
            int broj=Integer.parseInt(broj1);
            
            System.out.print("Unesite iznos koji se uplacuje: ");
            String iznos1 = scan.nextLine();
            
            System.out.print("Unesite naziv filijale u kojoj se vrsi uplata: ");
            String naziv1 = scan.nextLine();
            
            System.out.print("Unesite svrhu uplate: ");
            String svrha1 = scan.nextLine();
            
            String iznos= iznos1.replace(' ', '_');
            String naziv= naziv1.replace(' ', '_');
            String svrha= svrha1.replace(' ', '_');
            
            URL url = new URL("http://localhost:8080/server/resources/zahtevi/zahtev8/" + broj +"/" + iznos +"/" + naziv + "/" + svrha);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            System.out.println(content.toString());
              
        } catch (MalformedURLException ex) {
            Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ProtocolException ex) {
            Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void zahtev9() {
        try {
            System.out.print("Unesite broj racuna sa kog vrsite isplatu: ");
            String broj1 = scan.nextLine();
            int broj=Integer.parseInt(broj1);
            
            System.out.print("Unesite iznos koji se isplacuje: ");
            String iznos1 = scan.nextLine();
            
            System.out.print("Unesite naziv filijale u kojoj se vrsi isplata: ");
            String naziv1 = scan.nextLine();
            
            System.out.print("Unesite svrhu isplate: ");
            String svrha1 = scan.nextLine();
            
            String iznos= iznos1.replace(' ', '_');
            String naziv= naziv1.replace(' ', '_');
            String svrha= svrha1.replace(' ', '_');
            
            URL url = new URL("http://localhost:8080/server/resources/zahtevi/zahtev9/" + broj +"/" + iznos +"/" + naziv + "/" + svrha);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            System.out.println(content.toString());
              
        } catch (MalformedURLException ex) {
            Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ProtocolException ex) {
            Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void zahtev13() {
       try {                 
            System.out.print("Unesite identifikator komitenta: ");
            String broj1 = scan.nextLine();
            int broj=Integer.parseInt(broj1);
               
            URL url = new URL("http://localhost:8080/server/resources/zahtevi/zahtev13/" + broj);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            
            in.close();
            String s=content.toString().replace('?', '\n');
            System.out.println(s);
              
        } catch (MalformedURLException ex) {
            Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ProtocolException ex) {
            Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void zahtev14() {
        try {                 
            System.out.print("Unesite broj racuna: ");
            String broj1 = scan.nextLine();
            int broj=Integer.parseInt(broj1);
               
            URL url = new URL("http://localhost:8080/server/resources/zahtevi/zahtev14/" + broj);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            
            in.close();
            String s=content.toString().replace('?', '\n');
            System.out.println(s);
              
        } catch (MalformedURLException ex) {
            Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ProtocolException ex) {
            Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void zahtev15() {
        try {                 
            System.out.println();
                      
            URL url = new URL("http://localhost:8080/server/resources/zahtevi/zahtev15");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            
            in.close();
            String s=content.toString().replace('?', '\n');
            System.out.println(s);
              
        } catch (MalformedURLException ex) {
            Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ProtocolException ex) {
            Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void zahtev16() {
        try {                 
            System.out.println();
                      
            URL url = new URL("http://localhost:8080/server/resources/zahtevi/zahtev16");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            
            in.close();
            String s=content.toString().replace('?', '\n');
            System.out.println(s);
              
        } catch (MalformedURLException ex) {
            Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ProtocolException ex) {
            Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
