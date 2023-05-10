package entiteti;

import entiteti.Komitent;
import entiteti.Prenos;
import entiteti.Transakcija;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-02-07T19:18:28")
@StaticMetamodel(Racun.class)
public class Racun_ { 

    public static volatile SingularAttribute<Racun, Integer> brojTransakcija;
    public static volatile SingularAttribute<Racun, BigDecimal> stanje;
    public static volatile SingularAttribute<Racun, Integer> idMesto;
    public static volatile SingularAttribute<Racun, Date> datumOtvaranja;
    public static volatile SingularAttribute<Racun, Integer> idRacun;
    public static volatile SingularAttribute<Racun, Komitent> idKomitent;
    public static volatile ListAttribute<Racun, Transakcija> transakcijaList;
    public static volatile SingularAttribute<Racun, BigDecimal> dozvoljeniMinus;
    public static volatile ListAttribute<Racun, Prenos> prenosList;
    public static volatile SingularAttribute<Racun, String> status;

}