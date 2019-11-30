package pl.grzegorz.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Osoba.
 */
@Entity
@Table(name = "osoba")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Osoba implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "imie")
    private String imie;

    @Column(name = "nazwisko")
    private String nazwisko;

    @Column(name = "telefon")
    private String telefon;

    @Column(name = "pesel")
    private String pesel;

    @OneToOne(fetch = FetchType.LAZY)

    @JoinColumn(unique = true)
    private Adres adres;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties("wlascicieles")
    private Sklep sklep;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImie() {
        return imie;
    }

    public Osoba imie(String imie) {
        this.imie = imie;
        return this;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public Osoba nazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
        return this;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public String getTelefon() {
        return telefon;
    }

    public Osoba telefon(String telefon) {
        this.telefon = telefon;
        return this;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getPesel() {
        return pesel;
    }

    public Osoba pesel(String pesel) {
        this.pesel = pesel;
        return this;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public Adres getAdres() {
        return adres;
    }

    public Osoba adres(Adres adres) {
        this.adres = adres;
        return this;
    }

    public void setAdres(Adres adres) {
        this.adres = adres;
    }

    public Sklep getSklep() {
        return sklep;
    }

    public Osoba sklep(Sklep sklep) {
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
        if (!(o instanceof Osoba)) {
            return false;
        }
        return id != null && id.equals(((Osoba) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Osoba{" +
            "id=" + getId() +
            ", imie='" + getImie() + "'" +
            ", nazwisko='" + getNazwisko() + "'" +
            ", telefon='" + getTelefon() + "'" +
            ", pesel='" + getPesel() + "'" +
            "}";
    }
}
