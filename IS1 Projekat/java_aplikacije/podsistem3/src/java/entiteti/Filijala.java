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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Mico
 */
@Entity
@Table(name = "filijala")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Filijala.findAll", query = "SELECT f FROM Filijala f"),
    @NamedQuery(name = "Filijala.findByIdFilijala", query = "SELECT f FROM Filijala f WHERE f.idFilijala = :idFilijala"),
    @NamedQuery(name = "Filijala.findByNaziv", query = "SELECT f FROM Filijala f WHERE f.naziv = :naziv"),
    @NamedQuery(name = "Filijala.findByAdresa", query = "SELECT f FROM Filijala f WHERE f.adresa = :adresa"),
    @NamedQuery(name = "Filijala.findByIdMesto", query = "SELECT f FROM Filijala f WHERE f.idMesto = :idMesto")})
public class Filijala implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IdFilijala")
    private Integer idFilijala;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Naziv")
    private String naziv;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Adresa")
    private String adresa;
    @Basic(optional = false)
    @NotNull
    @Column(name = "IdMesto")
    private int idMesto;

    public Filijala() {
    }

    public Filijala(Integer idFilijala) {
        this.idFilijala = idFilijala;
    }

    public Filijala(Integer idFilijala, String naziv, String adresa, int idMesto) {
        this.idFilijala = idFilijala;
        this.naziv = naziv;
        this.adresa = adresa;
        this.idMesto = idMesto;
    }

    public Integer getIdFilijala() {
        return idFilijala;
    }

    public void setIdFilijala(Integer idFilijala) {
        this.idFilijala = idFilijala;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public int getIdMesto() {
        return idMesto;
    }

    public void setIdMesto(int idMesto) {
        this.idMesto = idMesto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idFilijala != null ? idFilijala.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Filijala)) {
            return false;
        }
        Filijala other = (Filijala) object;
        if ((this.idFilijala == null && other.idFilijala != null) || (this.idFilijala != null && !this.idFilijala.equals(other.idFilijala))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Filijala[ idFilijala=" + idFilijala + " ]";
    }
    
}
