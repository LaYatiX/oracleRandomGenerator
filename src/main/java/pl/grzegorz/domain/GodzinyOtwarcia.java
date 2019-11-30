package pl.grzegorz.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A GodzinyOtwarcia.
 */
@Entity
@Table(name = "godziny_otwarcia")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class GodzinyOtwarcia implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "poniedzialek")
    private String poniedzialek;

    @Column(name = "wtorek")
    private String wtorek;

    @Column(name = "sroda")
    private String sroda;

    @Column(name = "czwartek")
    private String czwartek;

    @Column(name = "piatek")
    private String piatek;

    @Column(name = "sobota")
    private String sobota;

    @Column(name = "niedziela")
    private String niedziela;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPoniedzialek() {
        return poniedzialek;
    }

    public GodzinyOtwarcia poniedzialek(String poniedzialek) {
        this.poniedzialek = poniedzialek;
        return this;
    }

    public void setPoniedzialek(String poniedzialek) {
        this.poniedzialek = poniedzialek;
    }

    public String getWtorek() {
        return wtorek;
    }

    public GodzinyOtwarcia wtorek(String wtorek) {
        this.wtorek = wtorek;
        return this;
    }

    public void setWtorek(String wtorek) {
        this.wtorek = wtorek;
    }

    public String getSroda() {
        return sroda;
    }

    public GodzinyOtwarcia sroda(String sroda) {
        this.sroda = sroda;
        return this;
    }

    public void setSroda(String sroda) {
        this.sroda = sroda;
    }

    public String getCzwartek() {
        return czwartek;
    }

    public GodzinyOtwarcia czwartek(String czwartek) {
        this.czwartek = czwartek;
        return this;
    }

    public void setCzwartek(String czwartek) {
        this.czwartek = czwartek;
    }

    public String getPiatek() {
        return piatek;
    }

    public GodzinyOtwarcia piatek(String piatek) {
        this.piatek = piatek;
        return this;
    }

    public void setPiatek(String piatek) {
        this.piatek = piatek;
    }

    public String getSobota() {
        return sobota;
    }

    public GodzinyOtwarcia sobota(String sobota) {
        this.sobota = sobota;
        return this;
    }

    public void setSobota(String sobota) {
        this.sobota = sobota;
    }

    public String getNiedziela() {
        return niedziela;
    }

    public GodzinyOtwarcia niedziela(String niedziela) {
        this.niedziela = niedziela;
        return this;
    }

    public void setNiedziela(String niedziela) {
        this.niedziela = niedziela;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GodzinyOtwarcia)) {
            return false;
        }
        return id != null && id.equals(((GodzinyOtwarcia) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "GodzinyOtwarcia{" +
            "id=" + getId() +
            ", poniedzialek='" + getPoniedzialek() + "'" +
            ", wtorek='" + getWtorek() + "'" +
            ", sroda='" + getSroda() + "'" +
            ", czwartek='" + getCzwartek() + "'" +
            ", piatek='" + getPiatek() + "'" +
            ", sobota='" + getSobota() + "'" +
            ", niedziela='" + getNiedziela() + "'" +
            "}";
    }
}
