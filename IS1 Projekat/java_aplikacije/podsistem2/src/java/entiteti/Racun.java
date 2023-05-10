/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entiteti;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Mico
 */
@Entity
@Table(name = "racun")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Racun.findAll", query = "SELECT r FROM Racun r"),
    @NamedQuery(name = "Racun.findByIdRacun", query = "SELECT r FROM Racun r WHERE r.idRacun = :idRacun"),
    @NamedQuery(name = "Racun.findByStanje", query = "SELECT r FROM Racun r WHERE r.stanje = :stanje"),
    @NamedQuery(name = "Racun.findByDozvoljeniMinus", query = "SELECT r FROM Racun r WHERE r.dozvoljeniMinus = :dozvoljeniMinus"),
    @NamedQuery(name = "Racun.findByStatus", query = "SELECT r FROM Racun r WHERE r.status = :status"),
    @NamedQuery(name = "Racun.findByDatumOtvaranja", query = "SELECT r FROM Racun r WHERE r.datumOtvaranja = :datumOtvaranja"),
    @NamedQuery(name = "Racun.findByBrojTransakcija", query = "SELECT r FROM Racun r WHERE r.brojTransakcija = :brojTransakcija"),
    @NamedQuery(name = "Racun.findByIdMesto", query = "SELECT r FROM Racun r WHERE r.idMesto = :idMesto")})
public class Racun implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IdRacun")
    private Integer idRacun;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "Stanje")
    private BigDecimal stanje;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DozvoljeniMinus")
    private BigDecimal dozvoljeniMinus;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "Status")
    private String status;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DatumOtvaranja")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datumOtvaranja;
    @Basic(optional = false)
    @NotNull
    @Column(name = "BrojTransakcija")
    private int brojTransakcija;
    @Basic(optional = false)
    @NotNull
    @Column(name = "IdMesto")
    private int idMesto;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idRacun")
    private List<Transakcija> transakcijaList;
    @JoinColumn(name = "IdKomitent", referencedColumnName = "IdKomitent")
    @ManyToOne(optional = false)
    private Komitent idKomitent;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idRacunSa")
    private List<Prenos> prenosList;

    public Racun() {
    }

    public Racun(Integer idRacun) {
        this.idRacun = idRacun;
    }

    public Racun(Integer idRacun, BigDecimal stanje, BigDecimal dozvoljeniMinus, String status, Date datumOtvaranja, int brojTransakcija, int idMesto) {
        this.idRacun = idRacun;
        this.stanje = stanje;
        this.dozvoljeniMinus = dozvoljeniMinus;
        this.status = status;
        this.datumOtvaranja = datumOtvaranja;
        this.brojTransakcija = brojTransakcija;
        this.idMesto = idMesto;
    }

    public Integer getIdRacun() {
        return idRacun;
    }

    public void setIdRacun(Integer idRacun) {
        this.idRacun = idRacun;
    }

    public BigDecimal getStanje() {
        return stanje;
    }

    public void setStanje(BigDecimal stanje) {
        this.stanje = stanje;
    }

    public BigDecimal getDozvoljeniMinus() {
        return dozvoljeniMinus;
    }

    public void setDozvoljeniMinus(BigDecimal dozvoljeniMinus) {
        this.dozvoljeniMinus = dozvoljeniMinus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDatumOtvaranja() {
        return datumOtvaranja;
    }

    public void setDatumOtvaranja(Date datumOtvaranja) {
        this.datumOtvaranja = datumOtvaranja;
    }

    public int getBrojTransakcija() {
        return brojTransakcija;
    }

    public void setBrojTransakcija(int brojTransakcija) {
        this.brojTransakcija = brojTransakcija;
    }

    public int getIdMesto() {
        return idMesto;
    }

    public void setIdMesto(int idMesto) {
        this.idMesto = idMesto;
    }

    @XmlTransient
    public List<Transakcija> getTransakcijaList() {
        return transakcijaList;
    }

    public void setTransakcijaList(List<Transakcija> transakcijaList) {
        this.transakcijaList = transakcijaList;
    }

    public Komitent getIdKomitent() {
        return idKomitent;
    }

    public void setIdKomitent(Komitent idKomitent) {
        this.idKomitent = idKomitent;
    }

    @XmlTransient
    public List<Prenos> getPrenosList() {
        return prenosList;
    }

    public void setPrenosList(List<Prenos> prenosList) {
        this.prenosList = prenosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRacun != null ? idRacun.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Racun)) {
            return false;
        }
        Racun other = (Racun) object;
        if ((this.idRacun == null && other.idRacun != null) || (this.idRacun != null && !this.idRacun.equals(other.idRacun))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Racun[ idRacun=" + idRacun + " ]";
    }
    
}
