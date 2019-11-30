package pl.grzegorz.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Adres.
 */
@Entity
@Table(name = "adres")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Adres implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "miasto")
    private String miasto;

    @Column(name = "ulica")
    private String ulica;

    @Column(name = "nr_domu")
    private String nrDomu;

    @Column(name = "nr_lokalu")
    private String nrLokalu;

    @Column(name = "wojewodzwtwo")
    private String wojewodzwtwo;

    @Column(name = "powiat")
    private String powiat;

    @Column(name = "gmina")
    private String gmina;

    @Column(name = "kod_pocztowy")
    private String kodPocztowy;

    @Column(name = "kraj")
    private String kraj;

    @OneToOne(fetch = FetchType.LAZY)

    @JoinColumn(unique = true)
    private Lokalizacja lokalizacja;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMiasto() {
        return miasto;
    }

    public Adres miasto(String miasto) {
        this.miasto = miasto;
        return this;
    }

    public void setMiasto(String miasto) {
        this.miasto = miasto;
    }

    public String getUlica() {
        return ulica;
    }

    public Adres ulica(String ulica) {
        this.ulica = ulica;
        return this;
    }

    public void setUlica(String ulica) {
        this.ulica = ulica;
    }

    public String getNrDomu() {
        return nrDomu;
    }

    public Adres nrDomu(String nrDomu) {
        this.nrDomu = nrDomu;
        return this;
    }

    public void setNrDomu(String nrDomu) {
        this.nrDomu = nrDomu;
    }

    public String getNrLokalu() {
        return nrLokalu;
    }

    public Adres nrLokalu(String nrLokalu) {
        this.nrLokalu = nrLokalu;
        return this;
    }

    public void setNrLokalu(String nrLokalu) {
        this.nrLokalu = nrLokalu;
    }

    public String getWojewodzwtwo() {
        return wojewodzwtwo;
    }

    public Adres wojewodzwtwo(String wojewodzwtwo) {
        this.wojewodzwtwo = wojewodzwtwo;
        return this;
    }

    public void setWojewodzwtwo(String wojewodzwtwo) {
        this.wojewodzwtwo = wojewodzwtwo;
    }

    public String getPowiat() {
        return powiat;
    }

    public Adres powiat(String powiat) {
        this.powiat = powiat;
        return this;
    }

    public void setPowiat(String powiat) {
        this.powiat = powiat;
    }

    public String getGmina() {
        return gmina;
    }

    public Adres gmina(String gmina) {
        this.gmina = gmina;
        return this;
    }

    public void setGmina(String gmina) {
        this.gmina = gmina;
    }

    public String getKodPocztowy() {
        return kodPocztowy;
    }

    public Adres kodPocztowy(String kodPocztowy) {
        this.kodPocztowy = kodPocztowy;
        return this;
    }

    public void setKodPocztowy(String kodPocztowy) {
        this.kodPocztowy = kodPocztowy;
    }

    public String getKraj() {
        return kraj;
    }

    public Adres kraj(String kraj) {
        this.kraj = kraj;
        return this;
    }

    public void setKraj(String kraj) {
        this.kraj = kraj;
    }

    public Lokalizacja getLokalizacja() {
        return lokalizacja;
    }

    public Adres lokalizacja(Lokalizacja lokalizacja) {
        this.lokalizacja = lokalizacja;
        return this;
    }

    public void setLokalizacja(Lokalizacja lokalizacja) {
        this.lokalizacja = lokalizacja;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Adres)) {
            return false;
        }
        return id != null && id.equals(((Adres) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Adres{" +
            "id=" + getId() +
            ", miasto='" + getMiasto() + "'" +
            ", ulica='" + getUlica() + "'" +
            ", nrDomu='" + getNrDomu() + "'" +
            ", nrLokalu='" + getNrLokalu() + "'" +
            ", wojewodzwtwo='" + getWojewodzwtwo() + "'" +
            ", powiat='" + getPowiat() + "'" +
            ", gmina='" + getGmina() + "'" +
            ", kodPocztowy='" + getKodPocztowy() + "'" +
            ", kraj='" + getKraj() + "'" +
            "}";
    }
}
