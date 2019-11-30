package pl.grzegorz.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Mieszkanie.
 */
@Entity
@Table(name = "mieszkanie")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Mieszkanie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "metraz")
    private Integer metraz;

    @Column(name = "czy_lazienka")
    private Boolean czyLazienka;

    @Column(name = "czy_kuchnia")
    private Boolean czyKuchnia;

    @Column(name = "czy_wyposazone")
    private Boolean czyWyposazone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties("mieszkanias")
    private Nieruchomosc nieruchomosc;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMetraz() {
        return metraz;
    }

    public Mieszkanie metraz(Integer metraz) {
        this.metraz = metraz;
        return this;
    }

    public void setMetraz(Integer metraz) {
        this.metraz = metraz;
    }

    public Boolean isCzyLazienka() {
        return czyLazienka;
    }

    public Mieszkanie czyLazienka(Boolean czyLazienka) {
        this.czyLazienka = czyLazienka;
        return this;
    }

    public void setCzyLazienka(Boolean czyLazienka) {
        this.czyLazienka = czyLazienka;
    }

    public Boolean isCzyKuchnia() {
        return czyKuchnia;
    }

    public Mieszkanie czyKuchnia(Boolean czyKuchnia) {
        this.czyKuchnia = czyKuchnia;
        return this;
    }

    public void setCzyKuchnia(Boolean czyKuchnia) {
        this.czyKuchnia = czyKuchnia;
    }

    public Boolean isCzyWyposazone() {
        return czyWyposazone;
    }

    public Mieszkanie czyWyposazone(Boolean czyWyposazone) {
        this.czyWyposazone = czyWyposazone;
        return this;
    }

    public void setCzyWyposazone(Boolean czyWyposazone) {
        this.czyWyposazone = czyWyposazone;
    }

    public Nieruchomosc getNieruchomosc() {
        return nieruchomosc;
    }

    public Mieszkanie nieruchomosc(Nieruchomosc nieruchomosc) {
        this.nieruchomosc = nieruchomosc;
        return this;
    }

    public void setNieruchomosc(Nieruchomosc nieruchomosc) {
        this.nieruchomosc = nieruchomosc;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Mieszkanie)) {
            return false;
        }
        return id != null && id.equals(((Mieszkanie) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Mieszkanie{" +
            "id=" + getId() +
            ", metraz=" + getMetraz() +
            ", czyLazienka='" + isCzyLazienka() + "'" +
            ", czyKuchnia='" + isCzyKuchnia() + "'" +
            ", czyWyposazone='" + isCzyWyposazone() + "'" +
            "}";
    }
}
