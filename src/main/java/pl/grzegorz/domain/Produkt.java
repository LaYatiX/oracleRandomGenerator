package pl.grzegorz.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Produkt.
 */
@Entity
@Table(name = "produkt")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Produkt implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nazwa")
    private String nazwa;

    @Column(name = "wartosc")
    private Double wartosc;

    @Column(name = "vat")
    private Integer vat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties("produkties")
    private Transakcja transakcja;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties("produkties")
    private Sklep sklep;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNazwa() {
        return nazwa;
    }

    public Produkt nazwa(String nazwa) {
        this.nazwa = nazwa;
        return this;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public Double getWartosc() {
        return wartosc;
    }

    public Produkt wartosc(Double wartosc) {
        this.wartosc = wartosc;
        return this;
    }

    public void setWartosc(Double wartosc) {
        this.wartosc = wartosc;
    }

    public Integer getVat() {
        return vat;
    }

    public Produkt vat(Integer vat) {
        this.vat = vat;
        return this;
    }

    public void setVat(Integer vat) {
        this.vat = vat;
    }

    public Transakcja getTransakcja() {
        return transakcja;
    }

    public Produkt transakcja(Transakcja transakcja) {
        this.transakcja = transakcja;
        return this;
    }

    public void setTransakcja(Transakcja transakcja) {
        this.transakcja = transakcja;
    }

    public Sklep getSklep() {
        return sklep;
    }

    public Produkt sklep(Sklep sklep) {
        this.sklep = sklep;
        return this;
    }

    public void setSklep(Sklep sklep) {
        this.sklep = sklep;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Produkt)) {
            return false;
        }
        return id != null && id.equals(((Produkt) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Produkt{" +
            "id=" + getId() +
            ", nazwa='" + getNazwa() + "'" +
            ", wartosc=" + getWartosc() +
            ", vat=" + getVat() +
            "}";
    }
}
