/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guiklijent;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Label;
import static java.awt.Label.CENTER;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


/**
 *
 * @author Mico
 */
public class GuiKlijent extends JFrame{
    int ind;
    
    ButtonGroup opcije;
    JRadioButton opcija1;
    JRadioButton opcija2;
    JRadioButton opcija3;
    JRadioButton opcija4;
    JRadioButton opcija5;
    JRadioButton opcija6;
    JRadioButton opcija7;
    JRadioButton opcija8;
    JRadioButton opcija9;
    JRadioButton opcija10;
    JRadioButton opcija11;
    JRadioButton opcija12;
    JRadioButton opcija13;
    JRadioButton opcija14;
    JRadioButton opcija15;
    JRadioButton opcija16;
    
    JTextField polje1;
    JTextField polje2;
    JTextField polje3;
    JTextField polje4;
    
    JLabel labela1;
    JLabel labela2;
    JLabel labela3;
    JLabel labela4;
    
    JLabel labelaPoruka;
    
    JButton button;
    
    JPanel windowPanel;
    JPanel buttonPanel;
    JPanel labelPanel;
    JPanel textFieldPanel;
    JPanel dugmePanel;
    
    public GuiKlijent(){
        setBounds(500,200,750,500);
        this.setResizable(false);

        this.setTitle("Klijent");
        populateWindow();
        ind=0;
        pack();
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
               dispose();
            }       
        });
        
        this.setVisible(true); 
    }
    
    public static void main(String[] args) {
        GuiKlijent gui= new GuiKlijent();
    }

    private void populateWindow() {
        opcije= new ButtonGroup();
        windowPanel = new JPanel(new GridLayout(1, 4));
        
        buttonPanel= new JPanel(new GridLayout(16, 1));       
        dodajRadioButtone();
        
        labelPanel = new JPanel(new GridLayout(20, 1));
        dodajLabele();
               
        textFieldPanel = new JPanel(new GridLayout(20, 1));
        dodajTekstualnaPolja();
        
        dugmePanel= new JPanel(new GridLayout(17,1));
        dodajDugme();
        
        windowPanel.add(buttonPanel);
        windowPanel.add(labelPanel);
        windowPanel.add(textFieldPanel);
        windowPanel.add(dugmePanel);
        add(windowPanel);
        
        
    }

    private void dodajRadioButtone() {
        opcija1= new JRadioButton();     
        opcija1.setText("1. Kreiraj novo mesto");
        buttonPanel.add(opcija1);
        opcija1.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                labela1.setText("Naziv mesta");
                labela1.setVisible(true);
                labela2.setText("Postanski broj");
                labela2.setVisible(true);
                labela3.setText("");
                labela3.setVisible(false);
                labela4.setText("");
                labela4.setVisible(false);
                polje1.setVisible(true);
                polje1.setText("");
                polje2.setVisible(true);
                polje2.setText("");
                polje3.setVisible(false);
                polje3.setText("");
                polje4.setVisible(false);
                polje4.setText("");
                ind=1;
            }
        });
        opcije.add(opcija1);
        
        opcija2= new JRadioButton();    
        opcija2.setText("2. Kreiraj novu filijalu");
        buttonPanel.add(opcija2);
        opcija2.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                labela1.setText("Naziv filijale");
                labela1.setVisible(true);
                labela2.setText("Adresa filijale");
                labela2.setVisible(true);
                labela3.setText("Naziv mesta");
                labela3.setVisible(true);
                labela4.setText("");
                labela4.setVisible(false);
                polje1.setVisible(true);
                polje1.setText("");
                polje2.setVisible(true);
                polje2.setText("");
                polje3.setVisible(true);
                polje3.setText("");
                polje4.setVisible(false);
                polje4.setText("");
                ind=2;
            }
        });
        opcije.add(opcija2);
        
        opcija3= new JRadioButton();     
        opcija3.setText("3. Kreiraj novog komitenta");
        buttonPanel.add(opcija3);
        opcija3.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                labela1.setText("Naziv komitenta");
                labela1.setVisible(true);
                labela2.setText("Adresa komitenta");
                labela2.setVisible(true);
                labela3.setText("Naziv sedista");
                labela3.setVisible(true);
                labela4.setText("");
                labela4.setVisible(false);
                polje1.setVisible(true);
                polje1.setText("");
                polje2.setVisible(true);
                polje2.setText("");
                polje3.setVisible(true);
                polje3.setText("");
                polje4.setVisible(false);
                polje4.setText("");
                ind=3;
            }
        });
        opcije.add(opcija3);
        
        opcija4= new JRadioButton();    
        opcija4.setText("4. Promeni sediste komitentu");
        buttonPanel.add(opcija4);
        opcija4.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                labela1.setText("Naziv komitenta");
                labela1.setVisible(true);
                labela2.setText("Naziv novog sedista");
                labela2.setVisible(true);
                labela3.setText("");
                labela3.setVisible(false);
                labela4.setText("");
                labela4.setVisible(false);
                polje1.setVisible(true);
                polje1.setText("");
                polje2.setVisible(true);
                polje2.setText("");
                polje3.setVisible(false);
                polje3.setText("");
                polje4.setVisible(false);
                polje4.setText("");
                ind=4;
            }
        });
        opcije.add(opcija4);
        
        opcija5= new JRadioButton();     
        opcija5.setText("5. Otvori novi racun");
        buttonPanel.add(opcija5);
        opcija5.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                labela1.setText("Dozvoljeni minus");
                labela1.setVisible(true);
                labela2.setText("Naziv vlasnika");
                labela2.setVisible(true);
                labela3.setText("Mesto otvaranja");
                labela3.setVisible(true);
                labela4.setText("");
                labela4.setVisible(false);
                polje1.setVisible(true);
                polje1.setText("");
                polje2.setVisible(true);
                polje2.setText("");
                polje3.setVisible(true);
                polje3.setText("");
                polje4.setVisible(false);
                polje4.setText("");
                ind=5;
            }
        });
        opcije.add(opcija5);
        
        opcija6= new JRadioButton();    
        opcija6.setText("6. Zatvori racun");
        buttonPanel.add(opcija6);
        opcija6.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                labela1.setText("Broj racuna");
                labela1.setVisible(true);
                labela2.setText("");
                labela2.setVisible(false);
                labela3.setText("");
                labela3.setVisible(false);
                labela4.setText("");
                labela4.setVisible(false);
                polje1.setVisible(true);
                polje1.setText("");
                polje2.setVisible(false);
                polje2.setText("");
                polje3.setVisible(false);
                polje3.setText("");
                polje4.setVisible(false);
                polje4.setText("");
                ind=6;
            }
        });
        opcije.add(opcija6);
              
        opcija7= new JRadioButton();     
        opcija7.setText("7. Izvrsi prenos sa racuna na racun");
        buttonPanel.add(opcija7);
        opcija7.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                labela1.setText("Broj racuna sa kog se vrsi prenos");
                labela1.setVisible(true);
                labela2.setText("Broj racuna na koji se vrsi prenos");
                labela2.setVisible(true);
                labela3.setText("Iznos");
                labela3.setVisible(true);
                labela4.setText("Svrha");
                labela4.setVisible(true);
                polje1.setVisible(true);
                polje1.setText("");
                polje2.setVisible(true);
                polje2.setText("");
                polje3.setVisible(true);
                polje3.setText("");
                polje4.setVisible(true);
                polje4.setText("");
                ind=7;
            }
        });
        opcije.add(opcija7);
        
        opcija8= new JRadioButton();    
        opcija8.setText("8. Izvrsi uplatu na racun");
        buttonPanel.add(opcija8);
        opcija8.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                labela1.setText("Broj racuna na koji se vrsi uplata");
                labela1.setVisible(true);
                labela2.setText("Iznos");
                labela2.setVisible(true);
                labela3.setText("Naziv filijale");
                labela3.setVisible(true);
                labela4.setText("Svrha");
                labela4.setVisible(true);
                polje1.setVisible(true);
                polje1.setText("");
                polje2.setVisible(true);
                polje2.setText("");
                polje3.setVisible(true);
                polje3.setText("");
                polje4.setVisible(true);
                polje4.setText("");
                ind=8;
            }
        });
        opcije.add(opcija8);
        
        opcija9= new JRadioButton();     
        opcija9.setText("9. Izvrsi isplatu sa racuna");
        buttonPanel.add(opcija9);
        opcija9.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                labela1.setText("Broj racuna sa kog se vrsi isplata");
                labela1.setVisible(true);
                labela2.setText("Iznos");
                labela2.setVisible(true);
                labela3.setText("Naziv filijale");
                labela3.setVisible(true);
                labela4.setText("Svrha");
                labela4.setVisible(true);
                polje1.setVisible(true);
                polje1.setText("");
                polje2.setVisible(true);
                polje2.setText("");
                polje3.setVisible(true);
                polje3.setText("");
                polje4.setVisible(true);
                polje4.setText("");
                ind=9;
            }
        });
        opcije.add(opcija9);
        
        opcija10= new JRadioButton();    
        opcija10.setText("10. Dohvati podatke o mestima");
        buttonPanel.add(opcija10);
        opcija10.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                labela1.setText("");
                labela1.setVisible(false);
                labela2.setText("");
                labela2.setVisible(false);
                labela3.setText("");
                labela3.setVisible(false);
                labela4.setText("");
                labela4.setVisible(false);
                polje1.setVisible(false);
                polje1.setText("");
                polje2.setVisible(false);
                polje2.setText("");
                polje3.setVisible(false);
                polje3.setText("");
                polje4.setVisible(false);
                polje4.setText("");
                ind=10;
            }
        });
        opcije.add(opcija10);
        
        opcija11= new JRadioButton();     
        opcija11.setText("11. Dohvati podatke o filijalama");
        buttonPanel.add(opcija11);
        opcija11.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                labela1.setText("");
                labela1.setVisible(false);
                labela2.setText("");
                labela2.setVisible(false);
                labela3.setText("");
                labela3.setVisible(false);
                labela4.setText("");
                labela4.setVisible(false);
                polje1.setVisible(false);
                polje1.setText("");
                polje2.setVisible(false);
                polje2.setText("");
                polje3.setVisible(false);
                polje3.setText("");
                polje4.setVisible(false);
                polje4.setText("");
                ind=11;
            }
        });
        opcije.add(opcija11);
        
        opcija12= new JRadioButton();    
        opcija12.setText("12. Dohvati podatke o komitentima");
        buttonPanel.add(opcija12);
        opcija12.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                labela1.setText("");
                labela1.setVisible(false);
                labela2.setText("");
                labela2.setVisible(false);
                labela3.setText("");
                labela3.setVisible(false);
                labela4.setText("");
                labela4.setVisible(false);
                polje1.setVisible(false);
                polje1.setText("");
                polje2.setVisible(false);
                polje2.setText("");
                polje3.setVisible(false);
                polje3.setText("");
                polje4.setVisible(false);
                polje4.setText("");
                ind=12;
            }
        });
        opcije.add(opcija12);
        
        opcija13= new JRadioButton();    
        opcija13.setText("13. Dohvati sve racune komitenta");
        buttonPanel.add(opcija13);
        opcija13.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                labela1.setText("Identifikator komitenta");
                labela1.setVisible(true);
                labela2.setText("");
                labela2.setVisible(false);
                labela3.setText("");
                labela3.setVisible(false);
                labela4.setText("");
                labela4.setVisible(false);
                polje1.setVisible(true);
                polje1.setText("");
                polje2.setVisible(false);
                polje2.setText("");
                polje3.setVisible(false);
                polje3.setText("");
                polje4.setVisible(false);
                polje4.setText("");
                ind=13;
            }
        });
        opcije.add(opcija13);
        
        opcija14= new JRadioButton();    
        opcija14.setText("14. Dohvati sve transakcije racuna");
        buttonPanel.add(opcija14);
        opcija14.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                labela1.setText("Broj racuna");
                labela1.setVisible(true);
                labela2.setText("");
                labela2.setVisible(false);
                labela3.setText("");
                labela3.setVisible(false);
                labela4.setText("");
                labela4.setVisible(false);
                polje1.setVisible(true);
                polje1.setText("");
                polje2.setVisible(false);
                polje2.setText("");
                polje3.setVisible(false);
                polje3.setText("");
                polje4.setVisible(false);
                polje4.setText("");
                ind=14;
            }
        });
        opcije.add(opcija14);
        
        opcija15= new JRadioButton();    
        opcija15.setText("15. Dohvati sve podatke iz rezervne kopije");
        buttonPanel.add(opcija15);
        opcija15.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                labela1.setText("");
                labela1.setVisible(false);
                labela2.setText("");
                labela2.setVisible(false);
                labela3.setText("");
                labela3.setVisible(false);
                labela4.setText("");
                labela4.setVisible(false);
                polje1.setVisible(false);
                polje1.setText("");
                polje2.setVisible(false);
                polje2.setText("");
                polje3.setVisible(false);
                polje3.setText("");
                polje4.setVisible(false);
                polje4.setText("");
                ind=15;
            }
        });
        opcije.add(opcija15);
        
        opcija16= new JRadioButton();    
        opcija16.setText("16. Dohvati sve razlike između originala i kopije");
        buttonPanel.add(opcija16);
        opcija16.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                labela1.setText("");
                labela1.setVisible(false);
                labela2.setText("");
                labela2.setVisible(false);
                labela3.setText("");
                labela3.setVisible(false);
                labela4.setText("");
                labela4.setVisible(false);
                polje1.setVisible(false);
                polje1.setText("");
                polje2.setVisible(false);
                polje2.setText("");
                polje3.setVisible(false);
                polje3.setText("");
                polje4.setVisible(false);
                polje4.setText("");
                ind=16;
            }
        });
        opcije.add(opcija16);
    }

    private void dodajLabele() {
        labela1= new JLabel("/",SwingConstants.CENTER);
        labela2= new JLabel("/",SwingConstants.CENTER);
        labela3= new JLabel("/",SwingConstants.CENTER);
        labela4= new JLabel("/",SwingConstants.CENTER);
        
        for(int i=0;i<2;i++)
            labelPanel.add(new JPanel());
        labelPanel.add(labela1);
        for(int i=0;i<4;i++)
            labelPanel.add(new JPanel());
        labelPanel.add(labela2);
        for(int i=0;i<4;i++)
            labelPanel.add(new JPanel());
        labelPanel.add(labela3);
        for(int i=0;i<4;i++)
            labelPanel.add(new JPanel());
        labelPanel.add(labela4);
        for(int i=0;i<2;i++)
            labelPanel.add(new JPanel());
        
    }

    private void dodajTekstualnaPolja() {
        polje1= new JTextField();
        polje2= new JTextField();
        polje3= new JTextField();
        polje4= new JTextField();
               
        for(int i=0;i<2;i++)
            textFieldPanel.add(new JPanel());
        textFieldPanel.add(polje1);
        for(int i=0;i<4;i++)
            textFieldPanel.add(new JPanel());
        textFieldPanel.add(polje2);
        for(int i=0;i<4;i++)
            textFieldPanel.add(new JPanel());
        textFieldPanel.add(polje3);
        for(int i=0;i<4;i++)
            textFieldPanel.add(new JPanel());
        textFieldPanel.add(polje4);
        for(int i=0;i<2;i++)
            textFieldPanel.add(new JPanel());
        
    }

    private void dodajDugme() {
        button=new JButton();
        button.setText("Obradi zahtev");
        for(int i=0;i<8;i++)
            dugmePanel.add(new JPanel());
        
        dugmePanel.add(button);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch(ind){
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
                    default: break;
                }
            }
        });
        for(int i=0;i<4;i++)
            dugmePanel.add(new JPanel());
        labelaPoruka= new JLabel("/",SwingConstants.CENTER);
   
        dugmePanel.add(labelaPoruka);
        for(int i=0;i<3;i++)
            dugmePanel.add(new JPanel());
    }
    
    
    private void zahtev1(){
         try {
            button.setEnabled(false);
            labelaPoruka.setText("Obrada...");
            String naziv1;
            int broj;
            while(true){
                naziv1 = polje1.getText();
                String br = polje2.getText();
                try{
                    broj=Integer.parseInt(br);
                    break;
                }
                catch(Exception e){
                    labelaPoruka.setText("Neispravan unos broja");
                    button.setEnabled(true);
                    return;
                }
            }
            labelaPoruka.setText("Obrada...");
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
            labelaPoruka.setText("Zahtev izvrsen");
            button.setEnabled(true);
            
            System.out.println("+++++++++++++++++++++++++");
            System.out.println();
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(GuiKlijent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GuiKlijent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void zahtev2(){
        try {
            button.setEnabled(false);
            labelaPoruka.setText("Obrada...");
            String naziv1 = polje1.getText();
            String adresa1 = polje2.getText();
            String mesto1 = polje3.getText();
            
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
            labelaPoruka.setText("Zahtev izvrsen");
            button.setEnabled(true);
            
            System.out.println("+++++++++++++++++++++++++");
            System.out.println();
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(GuiKlijent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ProtocolException ex) {
            Logger.getLogger(GuiKlijent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GuiKlijent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
    private void zahtev3() {
        try {
            button.setEnabled(false);
            labelaPoruka.setText("Obrada...");
            String naziv1 = polje1.getText();
            String adresa1 = polje2.getText();
            String mesto1 = polje3.getText();
            
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
            labelaPoruka.setText("Zahtev izvrsen");
            button.setEnabled(true);
            
            System.out.println("+++++++++++++++++++++++++");
            System.out.println();
            
              
        } catch (MalformedURLException ex) {
            Logger.getLogger(GuiKlijent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ProtocolException ex) {
            Logger.getLogger(GuiKlijent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GuiKlijent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    private void zahtev4() {
       try {
            button.setEnabled(false);
            labelaPoruka.setText("Obrada...");
            String naziv1 = polje1.getText();
            String mesto1 = polje2.getText();
                     
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
            
            labelaPoruka.setText("Zahtev izvrsen");
            button.setEnabled(true);
            
            System.out.println("+++++++++++++++++++++++++");
            System.out.println();
              
        } catch (MalformedURLException ex) {
            Logger.getLogger(GuiKlijent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ProtocolException ex) {
            Logger.getLogger(GuiKlijent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GuiKlijent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void zahtev10() {
        try {
            button.setEnabled(false);
            labelaPoruka.setText("Obrada...");
              
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
            labelaPoruka.setText("Zahtev izvrsen");
            button.setEnabled(true);
            
            System.out.println("+++++++++++++++++++++++++");
            System.out.println();
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(GuiKlijent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ProtocolException ex) {
            Logger.getLogger(GuiKlijent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GuiKlijent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private  void zahtev11() {
        try {
            button.setEnabled(false);
            labelaPoruka.setText("Obrada...");
              
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
            labelaPoruka.setText("Zahtev izvrsen");
            button.setEnabled(true);
            
            System.out.println("+++++++++++++++++++++++++");
            System.out.println();
              
        } catch (MalformedURLException ex) {
            Logger.getLogger(GuiKlijent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ProtocolException ex) {
            Logger.getLogger(GuiKlijent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GuiKlijent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     private void zahtev12() {
       try {
            button.setEnabled(false);
            labelaPoruka.setText("Obrada...");
              
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
            
            labelaPoruka.setText("Zahtev izvrsen");
            button.setEnabled(true);
            
            System.out.println("+++++++++++++++++++++++++");
            System.out.println();
              
        } catch (MalformedURLException ex) {
            Logger.getLogger(GuiKlijent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ProtocolException ex) {
            Logger.getLogger(GuiKlijent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GuiKlijent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
     
      private void zahtev5() {
        try {
            
            button.setEnabled(false);
            labelaPoruka.setText("Obrada...");
            String minus1;
            double min;
            while(true){
                minus1 = polje1.getText();
                try{
                    min=Double.parseDouble(minus1);
                    break;
                }
                catch(Exception e){
                    labelaPoruka.setText("Neispravan unos broja");
                    button.setEnabled(true);
                    return;
                }
            }
            
            String naziv1 = polje2.getText();       
            String mesto1 = polje3.getText();
            
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
            labelaPoruka.setText("Zahtev izvrsen");
            button.setEnabled(true);
            
            System.out.println("+++++++++++++++++++++++++");
            System.out.println();
              
        } catch (MalformedURLException ex) {
            Logger.getLogger(GuiKlijent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ProtocolException ex) {
            Logger.getLogger(GuiKlijent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GuiKlijent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
      
    
     private void zahtev6() {
        try {
            button.setEnabled(false);
            labelaPoruka.setText("Obrada...");
            int broj;
            while(true){
                String br = polje1.getText();
                try{
                    broj=Integer.parseInt(br);
                    break;
                }
                catch(Exception e){
                    labelaPoruka.setText("Neispravan unos broja");
                    button.setEnabled(true);
                    return;
                }
            }
            labelaPoruka.setText("Obrada...");
            
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
            
            labelaPoruka.setText("Zahtev izvrsen");
            button.setEnabled(true);
            
            System.out.println("+++++++++++++++++++++++++");
            System.out.println();
              
        } catch (MalformedURLException ex) {
            Logger.getLogger(GuiKlijent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ProtocolException ex) {
            Logger.getLogger(GuiKlijent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GuiKlijent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
     
      private void zahtev7() {
        try {
            button.setEnabled(false);
            labelaPoruka.setText("Obrada...");
            int broj,brojj;
            String broj3,svrha1;
            double pomoc;
            while(true){
                String broj1=polje1.getText();
                String broj2=polje2.getText();
                broj3=polje3.getText();
                svrha1=polje4.getText();
                try{
                    broj=Integer.parseInt(broj1);
                    brojj=Integer.parseInt(broj2);
                    pomoc=Double.parseDouble(broj3);
                    break;
                }
                catch(Exception e){
                    labelaPoruka.setText("Neispravan unos broja");
                    button.setEnabled(true);
                    return;
                }
            }
            labelaPoruka.setText("Obrada...");
            
                        
            String brojjj=broj3.replace(' ', '_');
            String svrha= svrha1.replace(' ', '_');
            
            URL url = new URL("http://localhost:8080/server/resources/zahtevi/zahtev7/" + brojj +"/" + broj +"/" + brojjj + "/" + svrha);
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
            
            labelaPoruka.setText("Zahtev izvrsen");
            button.setEnabled(true);
            
            System.out.println("+++++++++++++++++++++++++");
            System.out.println();
              
        } catch (MalformedURLException ex) {
            Logger.getLogger(GuiKlijent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ProtocolException ex) {
            Logger.getLogger(GuiKlijent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GuiKlijent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
      
      
      private void zahtev8() {
       try {     
            button.setEnabled(false);
            labelaPoruka.setText("Obrada...");
            int broj;
            String iznos1,naziv1,svrha1;
            double pom;
           while(true){
                String broj1=polje1.getText();
                iznos1=polje2.getText();
                naziv1=polje3.getText();
                svrha1=polje4.getText();
                try{
                    broj=Integer.parseInt(broj1);
                    pom=Double.parseDouble(iznos1);
                    break;
                }
                catch(Exception e){
                    labelaPoruka.setText("Neispravan unos broja");
                    button.setEnabled(true);
                    return;
                }
            }
           
            labelaPoruka.setText("Obrada...");
            
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
            
            labelaPoruka.setText("Zahtev izvrsen");
            button.setEnabled(true);
            
            System.out.println("+++++++++++++++++++++++++");
            System.out.println();
              
        } catch (MalformedURLException ex) {
            Logger.getLogger(GuiKlijent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ProtocolException ex) {
            Logger.getLogger(GuiKlijent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GuiKlijent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
      
      private void zahtev9() {
       try {     
            button.setEnabled(false);
            labelaPoruka.setText("Obrada...");
            int broj;
            String iznos1,naziv1,svrha1;
            double pom;
           while(true){
                String broj1=polje1.getText();
                iznos1=polje2.getText();
                naziv1=polje3.getText();
                svrha1=polje4.getText();
                try{
                    broj=Integer.parseInt(broj1);
                    pom=Double.parseDouble(iznos1);
                    break;
                }
                catch(Exception e){
                    labelaPoruka.setText("Neispravan unos broja");
                    button.setEnabled(true);
                    return;
                }
            }
           
            labelaPoruka.setText("Obrada...");
            
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
            
            labelaPoruka.setText("Zahtev izvrsen");
            button.setEnabled(true);
            
            System.out.println("+++++++++++++++++++++++++");
            System.out.println();
              
        } catch (MalformedURLException ex) {
            Logger.getLogger(GuiKlijent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ProtocolException ex) {
            Logger.getLogger(GuiKlijent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GuiKlijent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
      
      
      private void zahtev13() {
       try {                 
            button.setEnabled(false);
            labelaPoruka.setText("Obrada...");
            int broj;
            while(true){
                String broj1=polje1.getText();
                try{
                    broj=Integer.parseInt(broj1);
                    break;
                }
                catch(Exception e){
                    labelaPoruka.setText("Neispravan unos broja");
                    button.setEnabled(true);
                    return;
                }
            }
           
            labelaPoruka.setText("Obrada...");
               
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
            
            labelaPoruka.setText("Zahtev izvrsen");
            button.setEnabled(true);
            
            System.out.println("+++++++++++++++++++++++++");
            System.out.println();
              
        } catch (MalformedURLException ex) {
            Logger.getLogger(GuiKlijent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ProtocolException ex) {
            Logger.getLogger(GuiKlijent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GuiKlijent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
      
      private void zahtev14() {
        try {                 
            button.setEnabled(false);
            labelaPoruka.setText("Obrada...");
            int broj;
            while(true){
                String broj1=polje1.getText();
                try{
                    broj=Integer.parseInt(broj1);
                    break;
                }
                catch(Exception e){
                    labelaPoruka.setText("Neispravan unos broja");
                    button.setEnabled(true);
                    return;
                }
            }
           
            labelaPoruka.setText("Obrada...");
               
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
            
            labelaPoruka.setText("Zahtev izvrsen");
            button.setEnabled(true);
            
            System.out.println("+++++++++++++++++++++++++");
            System.out.println();
              
        } catch (MalformedURLException ex) {
            Logger.getLogger(GuiKlijent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ProtocolException ex) {
            Logger.getLogger(GuiKlijent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GuiKlijent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
      
      
      private  void zahtev15() {
        try {                 
            button.setEnabled(false);
            labelaPoruka.setText("Obrada...");
                      
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
            
            labelaPoruka.setText("Zahtev izvrsen");
            button.setEnabled(true);
            
            System.out.println("+++++++++++++++++++++++++");
            System.out.println();
              
        } catch (MalformedURLException ex) {
            Logger.getLogger(GuiKlijent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ProtocolException ex) {
            Logger.getLogger(GuiKlijent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GuiKlijent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    
      private  void zahtev16() {
        try {                 
            button.setEnabled(false);
            labelaPoruka.setText("Obrada...");
                      
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
            
            labelaPoruka.setText("Zahtev izvrsen");
            button.setEnabled(true);
            
            System.out.println("+++++++++++++++++++++++++");
            System.out.println();
              
        } catch (MalformedURLException ex) {
            Logger.getLogger(GuiKlijent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ProtocolException ex) {
            Logger.getLogger(GuiKlijent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GuiKlijent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
    

