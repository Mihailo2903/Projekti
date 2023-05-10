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
@Table(name = "mesto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Mesto.findAll", query = "SELECT m FROM Mesto m"),
    @NamedQuery(name = "Mesto.findByIdMesto", query = "SELECT m FROM Mesto m WHERE m.idMesto = :idMesto"),
    @NamedQuery(name = "Mesto.findByPostanskiBroj", query = "SELECT m FROM Mesto m WHERE m.postanskiBroj = :postanskiBroj"),
    @NamedQuery(name = "Mesto.findByNaziv", query = "SELECT m FROM Mesto m WHERE m.naziv = :naziv")})
public class Mesto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IdMesto")
    private Integer idMesto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PostanskiBroj")
    private int postanskiBroj;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Naziv")
    private String naziv;

    public Mesto() {
    }

    public Mesto(Integer idMesto) {
        this.idMesto = idMesto;
    }

    public Mesto(Integer idMesto, int postanskiBroj, String naziv) {
        this.idMesto = idMesto;
        this.postanskiBroj = postanskiBroj;
        this.naziv = naziv;
    }

    public Integer getIdMesto() {
        return idMesto;
    }

    public void setIdMesto(Integer idMesto) {
        this.idMesto = idMesto;
    }

    public int getPostanskiBroj() {
        return postanskiBroj;
    }

    public void setPostanskiBroj(int postanskiBroj) {
        this.postanskiBroj = postanskiBroj;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMesto != null ? idMesto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Mesto)) {
            return false;
        }
        Mesto other = (Mesto) object;
        if ((this.idMesto == null && other.idMesto != null) || (this.idMesto != null && !this.idMesto.equals(other.idMesto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Mesto[ idMesto=" + idMesto + " ]";
    }
    
}
