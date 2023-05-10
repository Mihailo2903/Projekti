/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entiteti;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Mico
 */
@Entity
@Table(name = "isplata")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Isplata.findAll", query = "SELECT i FROM Isplata i"),
    @NamedQuery(name = "Isplata.findByIdTransakcija", query = "SELECT i FROM Isplata i WHERE i.idTransakcija = :idTransakcija"),
    @NamedQuery(name = "Isplata.findByIdFilijala", query = "SELECT i FROM Isplata i WHERE i.idFilijala = :idFilijala")})
public class Isplata implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IdTransakcija")
    private Integer idTransakcija;
    @Basic(optional = false)
    @NotNull
    @Column(name = "IdFilijala")
    private int idFilijala;
    @JoinColumn(name = "IdTransakcija", referencedColumnName = "IdTransakcija", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Transakcija transakcija;

    public Isplata() {
    }

    public Isplata(Integer idTransakcija) {
        this.idTransakcija = idTransakcija;
    }

    public Isplata(Integer idTransakcija, int idFilijala) {
        this.idTransakcija = idTransakcija;
        this.idFilijala = idFilijala;
    }

    public Integer getIdTransakcija() {
        return idTransakcija;
    }

    public void setIdTransakcija(Integer idTransakcija) {
        this.idTransakcija = idTransakcija;
    }

    public int getIdFilijala() {
        return idFilijala;
    }

    public void setIdFilijala(int idFilijala) {
        this.idFilijala = idFilijala;
    }

    public Transakcija getTransakcija() {
        return transakcija;
    }

    public void setTransakcija(Transakcija transakcija) {
        this.transakcija = transakcija;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTransakcija != null ? idTransakcija.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Isplata)) {
            return false;
        }
        Isplata other = (Isplata) object;
        if ((this.idTransakcija == null && other.idTransakcija != null) || (this.idTransakcija != null && !this.idTransakcija.equals(other.idTransakcija))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Isplata[ idTransakcija=" + idTransakcija + " ]";
    }
    
}
