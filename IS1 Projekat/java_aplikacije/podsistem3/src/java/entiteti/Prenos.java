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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Mico
 */
@Entity
@Table(name = "prenos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Prenos.findAll", query = "SELECT p FROM Prenos p"),
    @NamedQuery(name = "Prenos.findByIdTransakcija", query = "SELECT p FROM Prenos p WHERE p.idTransakcija = :idTransakcija"),
    @NamedQuery(name = "Prenos.findByIdRacunSa", query = "SELECT p FROM Prenos p WHERE p.idRacunSa = :idRacunSa")})
public class Prenos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IdTransakcija")
    private Integer idTransakcija;
    @Basic(optional = false)
    @NotNull
    @Column(name = "IdRacunSa")
    private int idRacunSa;

    public Prenos() {
    }

    public Prenos(Integer idTransakcija) {
        this.idTransakcija = idTransakcija;
    }

    public Prenos(Integer idTransakcija, int idRacunSa) {
        this.idTransakcija = idTransakcija;
        this.idRacunSa = idRacunSa;
    }

    public Integer getIdTransakcija() {
        return idTransakcija;
    }

    public void setIdTransakcija(Integer idTransakcija) {
        this.idTransakcija = idTransakcija;
    }

    public int getIdRacunSa() {
        return idRacunSa;
    }

    public void setIdRacunSa(int idRacunSa) {
        this.idRacunSa = idRacunSa;
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
        if (!(object instanceof Prenos)) {
            return false;
        }
        Prenos other = (Prenos) object;
        if ((this.idTransakcija == null && other.idTransakcija != null) || (this.idTransakcija != null && !this.idTransakcija.equals(other.idTransakcija))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Prenos[ idTransakcija=" + idTransakcija + " ]";
    }
    
}
