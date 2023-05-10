package entiteti;

import entiteti.Isplata;
import entiteti.Prenos;
import entiteti.Racun;
import entiteti.Uplata;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-02-07T19:18:28")
@StaticMetamodel(Transakcija.class)
public class Transakcija_ { 

    public static volatile SingularAttribute<Transakcija, Isplata> isplata;
    public static volatile SingularAttribute<Transakcija, Integer> idTransakcija;
    public static volatile SingularAttribute<Transakcija, BigDecimal> iznos;
    public static volatile SingularAttribute<Transakcija, Date> datumVreme;
    public static volatile SingularAttribute<Transakcija, String> svrha;
    public static volatile SingularAttribute<Transakcija, Racun> idRacun;
    public static volatile SingularAttribute<Transakcija, Prenos> prenos;
    public static volatile SingularAttribute<Transakcija, Integer> redniBroj;
    public static volatile SingularAttribute<Transakcija, Uplata> uplata;

}