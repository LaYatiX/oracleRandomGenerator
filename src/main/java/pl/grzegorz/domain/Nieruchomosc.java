package pl.grzegorz.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import pl.grzegorz.domain.enumeration.TypNieruchomosci;

/**
 * A Nieruchomosc.
 */
@Entity
@Table(name = "nieruchomosc")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Nieruchomosc implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "typ")
    private TypNieruchomosci typ;

    @Column(name = "ilosc_mieszkan")
    private Integer iloscMieszkan;

    @Column(name = "ilosc_mieszkancow")
    private Integer iloscMieszkancow;

    @OneToOne(fetch = FetchType.LAZY)

    @JoinColumn(unique = true)
    private Adres adres;

    @OneToMany(mappedBy = "nieruchomosc")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Mieszkanie> mieszkanias = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TypNieruchomosci getTyp() {
        return typ;
    }

    public Nieruchomosc typ(TypNieruchomosci typ) {
        this.typ = typ;
        return this;
    }

    public void setTyp(TypNieruchomosci typ) {
        this.typ = typ;
    }

    public Integer getIloscMieszkan() {
        return iloscMieszkan;
    }

    public Nieruchomosc iloscMieszkan(Integer iloscMieszkan) {
        this.iloscMieszkan = iloscMieszkan;
        return this;
    }

    public void setIloscMieszkan(Integer iloscMieszkan) {
        this.iloscMieszkan = iloscMieszkan;
    }

    public Integer getIloscMieszkancow() {
        return iloscMieszkancow;
    }

    public Nieruchomosc iloscMieszkancow(Integer iloscMieszkancow) {
        this.iloscMieszkancow = iloscMieszkancow;
        return this;
    }

    public void setIloscMieszkancow(Integer iloscMieszkancow) {
        this.iloscMieszkancow = iloscMieszkancow;
    }

    public Adres getAdres() {
        return adres;
    }

    public Nieruchomosc adres(Adres adres) {
        this.adres = adres;
        return this;
    }

    public void setAdres(Adres adres) {
        this.adres = adres;
    }

    public Set<Mieszkanie> getMieszkanias() {
        return mieszkanias;
    }

    public Nieruchomosc mieszkanias(Set<Mieszkanie> mieszkanies) {
        this.mieszkanias = mieszkanies;
        return this;
    }

    public Nieruchomosc addMieszkania(Mieszkanie mieszkanie) {
        this.mieszkanias.add(mieszkanie);
        mieszkanie.setNieruchomosc(this);
        return this;
    }

    public Nieruchomosc removeMieszkania(Mieszkanie mieszkanie) {
        this.mieszkanias.remove(mieszkanie);
        mieszkanie.setNieruchomosc(null);
        return this;
    }

    public void setMieszkanias(Set<Mieszkanie> mieszkanies) {
        this.mieszkanias = mieszkanies;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Nieruchomosc)) {
            return false;
        }
        return id != null && id.equals(((Nieruchomosc) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Nieruchomosc{" +
            "id=" + getId() +
            ", typ='" + getTyp() + "'" +
            ", iloscMieszkan=" + getIloscMieszkan() +
            ", iloscMieszkancow=" + getIloscMieszkancow() +
            "}";
    }
}
