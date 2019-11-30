package pl.grzegorz.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import pl.grzegorz.domain.enumeration.TypSklepu;

/**
 * A Sklep.
 */
@Entity
@Table(name = "sklep")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Sklep implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "typ")
    private TypSklepu typ;

    @OneToOne(fetch = FetchType.LAZY)

    @JoinColumn(unique = true)
    private GodzinyOtwarcia godzinyOtwarcia;

    @OneToMany(mappedBy = "sklep")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Transakcja> transakcjes = new HashSet<>();

    @OneToMany(mappedBy = "sklep")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Produkt> produkties = new HashSet<>();

    @OneToMany(mappedBy = "sklep")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Osoba> wlascicieles = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TypSklepu getTyp() {
        return typ;
    }

    public Sklep typ(TypSklepu typ) {
        this.typ = typ;
        return this;
    }

    public void setTyp(TypSklepu typ) {
        this.typ = typ;
    }

    public GodzinyOtwarcia getGodzinyOtwarcia() {
        return godzinyOtwarcia;
    }

    public Sklep godzinyOtwarcia(GodzinyOtwarcia godzinyOtwarcia) {
        this.godzinyOtwarcia = godzinyOtwarcia;
        return this;
    }

    public void setGodzinyOtwarcia(GodzinyOtwarcia godzinyOtwarcia) {
        this.godzinyOtwarcia = godzinyOtwarcia;
    }

    public Set<Transakcja> getTransakcjes() {
        return transakcjes;
    }

    public Sklep transakcjes(Set<Transakcja> transakcjas) {
        this.transakcjes = transakcjas;
        return this;
    }

    public Sklep addTransakcje(Transakcja transakcja) {
        this.transakcjes.add(transakcja);
        transakcja.setSklep(this);
        return this;
    }

    public Sklep removeTransakcje(Transakcja transakcja) {
        this.transakcjes.remove(transakcja);
        transakcja.setSklep(null);
        return this;
    }

    public void setTransakcjes(Set<Transakcja> transakcjas) {
        this.transakcjes = transakcjas;
    }

    public Set<Produkt> getProdukties() {
        return produkties;
    }

    public Sklep produkties(Set<Produkt> produkts) {
        this.produkties = produkts;
        return this;
    }

    public Sklep addProdukty(Produkt produkt) {
        this.produkties.add(produkt);
        produkt.setSklep(this);
        return this;
    }

    public Sklep removeProdukty(Produkt produkt) {
        this.produkties.remove(produkt);
        produkt.setSklep(null);
        return this;
    }

    public void setProdukties(Set<Produkt> produkts) {
        this.produkties = produkts;
    }

    public Set<Osoba> getWlascicieles() {
        return wlascicieles;
    }

    public Sklep wlascicieles(Set<Osoba> osobas) {
        this.wlascicieles = osobas;
        return this;
    }

    public Sklep addWlasciciele(Osoba osoba) {
        this.wlascicieles.add(osoba);
        osoba.setSklep(this);
        return this;
    }

    public Sklep removeWlasciciele(Osoba osoba) {
        this.wlascicieles.remove(osoba);
        osoba.setSklep(null);
        return this;
    }

    public void setWlascicieles(Set<Osoba> osobas) {
        this.wlascicieles = osobas;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Sklep)) {
            return false;
        }
        return id != null && id.equals(((Sklep) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Sklep{" +
            "id=" + getId() +
            ", typ='" + getTyp() + "'" +
            "}";
    }
}
