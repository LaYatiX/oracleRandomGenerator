package pl.grzegorz.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Transakcja.
 */
@Entity
@Table(name = "transakcja")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Transakcja implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nettoo")
    private Double nettoo;

    @Column(name = "brutton")
    private Double brutton;

    @Column(name = "vat")
    private Integer vat;

    @OneToMany(mappedBy = "transakcja")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Produkt> produkties = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties("transakcjes")
    private Sklep sklep;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getNettoo() {
        return nettoo;
    }

    public Transakcja nettoo(Double nettoo) {
        this.nettoo = nettoo;
        return this;
    }

    public void setNettoo(Double nettoo) {
        this.nettoo = nettoo;
    }

    public Double getBrutton() {
        return brutton;
    }

    public Transakcja brutton(Double brutton) {
        this.brutton = brutton;
        return this;
    }

    public void setBrutton(Double brutton) {
        this.brutton = brutton;
    }

    public Integer getVat() {
        return vat;
    }

    public Transakcja vat(Integer vat) {
        this.vat = vat;
        return this;
    }

    public void setVat(Integer vat) {
        this.vat = vat;
    }

    public Set<Produkt> getProdukties() {
        return produkties;
    }

    public Transakcja produkties(Set<Produkt> produkts) {
        this.produkties = produkts;
        return this;
    }

    public Transakcja addProdukty(Produkt produkt) {
        this.produkties.add(produkt);
        produkt.setTransakcja(this);
        return this;
    }

    public Transakcja removeProdukty(Produkt produkt) {
        this.produkties.remove(produkt);
        produkt.setTransakcja(null);
        return this;
    }

    public void setProdukties(Set<Produkt> produkts) {
        this.produkties = produkts;
    }

    public Sklep getSklep() {
        return sklep;
    }

    public Transakcja sklep(Sklep sklep) {
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
        if (!(o instanceof Transakcja)) {
            return false;
        }
        return id != null && id.equals(((Transakcja) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Transakcja{" +
            "id=" + getId() +
            ", nettoo=" + getNettoo() +
            ", brutton=" + getBrutton() +
            ", vat=" + getVat() +
            "}";
    }
}
