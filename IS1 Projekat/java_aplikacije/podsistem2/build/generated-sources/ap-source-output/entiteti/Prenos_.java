package entiteti;

import entiteti.Racun;
import entiteti.Transakcija;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-02-07T19:18:28")
@StaticMetamodel(Prenos.class)
public class Prenos_ { 

    public static volatile SingularAttribute<Prenos, Integer> idTransakcija;
    public static volatile SingularAttribute<Prenos, Racun> idRacunSa;
    public static volatile SingularAttribute<Prenos, Transakcija> transakcija;

}