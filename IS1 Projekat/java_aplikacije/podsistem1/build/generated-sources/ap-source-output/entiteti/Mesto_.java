package entiteti;

import entiteti.Filijala;
import entiteti.Komitent;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-02-05T22:24:09")
@StaticMetamodel(Mesto.class)
public class Mesto_ { 

    public static volatile SingularAttribute<Mesto, Integer> idMesto;
    public static volatile SingularAttribute<Mesto, String> naziv;
    public static volatile SingularAttribute<Mesto, Integer> postanskiBroj;
    public static volatile ListAttribute<Mesto, Filijala> filijalaList;
    public static volatile ListAttribute<Mesto, Komitent> komitentList;

}