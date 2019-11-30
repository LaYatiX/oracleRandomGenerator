package pl.grzegorz.service.util;

import org.springframework.stereotype.Service;
import pl.grzegorz.domain.Adres;
import pl.grzegorz.domain.GodzinyOtwarcia;
import pl.grzegorz.domain.Lokalizacja;
import pl.grzegorz.domain.Mieszkanie;
import pl.grzegorz.domain.Nieruchomosc;
import pl.grzegorz.domain.Osoba;
import pl.grzegorz.domain.Produkt;
import pl.grzegorz.domain.Sklep;
import pl.grzegorz.domain.Transakcja;
import pl.grzegorz.domain.enumeration.TypNieruchomosci;
import pl.grzegorz.domain.enumeration.TypSklepu;
import pl.grzegorz.repository.AdresRepository;
import pl.grzegorz.repository.GodzinyOtwarciaRepository;
import pl.grzegorz.repository.LokalizacjaRepository;
import pl.grzegorz.repository.MieszkanieRepository;
import pl.grzegorz.repository.NieruchomoscRepository;
import pl.grzegorz.repository.OsobaRepository;
import pl.grzegorz.repository.ProduktRepository;
import pl.grzegorz.repository.SklepRepository;
import pl.grzegorz.repository.TransakcjaRepository;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


@Service
public class GenerateData {
    private final NieruchomoscRepository nieruchomoscRepository;
    private final AdresRepository adresRepository;
    private final GodzinyOtwarciaRepository godzinyOtwarciaRepository;
    private final LokalizacjaRepository lokalizacjaRepository;
    private final MieszkanieRepository mieszkanieRepository;
    private final OsobaRepository osobaRepository;
    private final ProduktRepository produktRepository;
    private final SklepRepository sklepRepository;
    private final TransakcjaRepository transakcjaRepository;

    public GenerateData(NieruchomoscRepository nieruchomoscRepository, AdresRepository adresRepository, GodzinyOtwarciaRepository godzinyOtwarciaRepository, LokalizacjaRepository lokalizacjaRepository, MieszkanieRepository mieszkanieRepository, OsobaRepository osobaRepository, ProduktRepository produktRepository, SklepRepository sklepRepository, TransakcjaRepository transakcjaRepository) {
        this.nieruchomoscRepository = nieruchomoscRepository;
        this.adresRepository = adresRepository;
        this.godzinyOtwarciaRepository = godzinyOtwarciaRepository;
        this.lokalizacjaRepository = lokalizacjaRepository;
        this.mieszkanieRepository = mieszkanieRepository;
        this.osobaRepository = osobaRepository;
        this.produktRepository = produktRepository;
        this.sklepRepository = sklepRepository;
        this.transakcjaRepository = transakcjaRepository;
    }

    @PostConstruct
    @Transactional
    public void generateManyData(){
        generateDataALotOfData(1000000);
    }

    @Transactional
    public void generateDataALotOfData (Integer amount){
        for (int i = 0; i < amount; i++) {
            generateData();
        }
    }

    @Transactional
    public void generateData (){
        Produkt produkt1 = generateProduct();
        Produkt produkt2 = generateProduct();
        Produkt produkt3 = generateProduct();
        Produkt produkt4 = generateProduct();
        Produkt produkt5 = generateProduct();
        Produkt produkt6 = generateProduct();
        Lokalizacja lokalizacja = generateLokalizacja();
        Adres adres = generateAdres(lokalizacja);

        Lokalizacja lokalizacja2 = generateLokalizacja();
        Adres adres2 = generateAdres(lokalizacja2);

        Lokalizacja lokalizacja3 = generateLokalizacja();
        Adres adres3 = generateAdres(lokalizacja3);

        Osoba osoba1 = generateOsoba();
        Osoba osoba2 = generateOsoba();
        Osoba osoba3 = generateOsoba();

        Mieszkanie mieszkanie1 = generateMieszkanie();
        Mieszkanie mieszkanie2 = generateMieszkanie();
        Mieszkanie mieszkanie3 = generateMieszkanie();
        GodzinyOtwarcia godzinyOtwarcia = generateGodzinyOtwarcia();
        Nieruchomosc nieruchomosc = generateNieruchomosc(adres, new HashSet<>(Arrays.asList(mieszkanie1, mieszkanie2, mieszkanie3)));
        Sklep sklep = generateSklep(godzinyOtwarcia, new HashSet<>(Arrays.asList(produkt1, produkt2, produkt3, produkt4, produkt5, produkt6)), new HashSet<>(), new HashSet<>(Arrays.asList(osoba1)));

        Transakcja transakcja1 = generateTransakcja(new HashSet<>(Arrays.asList(produkt1, produkt2, produkt3)), sklep);
        Transakcja transakcja2 = generateTransakcja(new HashSet<>(Arrays.asList(produkt4, produkt5, produkt3)), sklep);
        Transakcja transakcja3 = generateTransakcja(new HashSet<>(Arrays.asList(produkt5, produkt6, produkt3)), sklep);
    }

    @Transactional
    public Produkt generateProduct(){
        Produkt produkt = new Produkt();
        produkt.setNazwa(RandomUtil.generateRandomString());
        produkt.setVat(RandomUtil.generateRandomNumber());
        produkt.setWartosc(RandomUtil.generateRandomDouble());
        return produktRepository.save(produkt);
    }

    @Transactional
    public Sklep generateSklep(GodzinyOtwarcia godzinyOtwarcia, Set<Produkt> produkts, Set<Transakcja> transakcjas, Set<Osoba> wlasicicele){
        Sklep sklep = new Sklep();
        sklep.setGodzinyOtwarcia(godzinyOtwarcia);
        sklep.setTyp(RandomUtil.generateRandomTypSklepu());
        sklep.setProdukties(produkts);
        sklep.setTransakcjes(transakcjas);
        sklep.setWlascicieles(wlasicicele);
        return sklepRepository.save(sklep);
    }

    @Transactional
    public Nieruchomosc generateNieruchomosc(Adres adres, Set<Mieszkanie> mieszkanies){
        Nieruchomosc nieruchomosc = new Nieruchomosc();
        nieruchomosc.setAdres(adres);
        nieruchomosc.setIloscMieszkan(RandomUtil.generateRandomNumber());
        nieruchomosc.setIloscMieszkancow(RandomUtil.generateRandomNumber());
        nieruchomosc.setTyp(RandomUtil.generateRandomTypNieruchomosci());
        nieruchomosc.setMieszkanias(mieszkanies);
        final Nieruchomosc nieruchomoscsaved = nieruchomoscRepository.save(nieruchomosc);

        mieszkanies.stream().forEach(mieszkanie -> {
            mieszkanie.setNieruchomosc(nieruchomoscsaved);
            mieszkanieRepository.save(mieszkanie);
        });

        return nieruchomoscsaved;
    }

    @Transactional
    public Adres generateAdres (Lokalizacja lokalizacja){
        Adres adres = new Adres();
        adres.setGmina(RandomUtil.generateRandomString());
        adres.setKodPocztowy(RandomUtil.generateRandomString());
        adres.setKraj(RandomUtil.generateRandomString());
        adres.setLokalizacja(lokalizacja);
        adres.setMiasto(RandomUtil.generateRandomString());
        adres.setNrDomu(RandomUtil.generateRandomString());
        adres.setNrLokalu(RandomUtil.generateRandomString());
        adres.setPowiat(RandomUtil.generateRandomString());
        adres.setUlica(RandomUtil.generateRandomString());
        adres.setWojewodzwtwo(RandomUtil.generateRandomString());
        return adresRepository.save(adres);
    }

    @Transactional
    public GodzinyOtwarcia generateGodzinyOtwarcia (){
        GodzinyOtwarcia godzinyOtwarcia = new GodzinyOtwarcia();
        godzinyOtwarcia.setPoniedzialek(RandomUtil.generateRandomString());
        godzinyOtwarcia.setWtorek(RandomUtil.generateRandomString());
        godzinyOtwarcia.setSroda(RandomUtil.generateRandomString());
        godzinyOtwarcia.setCzwartek(RandomUtil.generateRandomString());
        godzinyOtwarcia.setPiatek(RandomUtil.generateRandomString());
        godzinyOtwarcia.setSobota(RandomUtil.generateRandomString());
        godzinyOtwarcia.setNiedziela(RandomUtil.generateRandomString());
        return godzinyOtwarciaRepository.save(godzinyOtwarcia);
    }

    @Transactional
    public Lokalizacja generateLokalizacja (){
        Lokalizacja lokalizacja = new Lokalizacja();
        lokalizacja.setLat(RandomUtil.generateRandomDouble());
        lokalizacja.setLng(RandomUtil.generateRandomDouble());
        return lokalizacjaRepository.save(lokalizacja);
    }

    @Transactional
    public Mieszkanie generateMieszkanie (){
        Mieszkanie mieszkanie = new Mieszkanie();
        mieszkanie.setCzyKuchnia(RandomUtil.generateRandomBoolean());
        mieszkanie.setCzyLazienka(RandomUtil.generateRandomBoolean());
        mieszkanie.setCzyWyposazone(RandomUtil.generateRandomBoolean());
        mieszkanie.setMetraz(RandomUtil.generateRandomNumber());
        return mieszkanieRepository.save(mieszkanie);
    }

    @Transactional
    public Osoba generateOsoba (){
        Osoba osoba = new Osoba();
        osoba.setImie(RandomUtil.generateRandomString());
        osoba.setNazwisko(RandomUtil.generateRandomString());
        osoba.setPesel(RandomUtil.generateRandomString());
        osoba.setTelefon(RandomUtil.generateRandomString());
        return osobaRepository.save(osoba);
    }

    @Transactional
    public Transakcja generateTransakcja (Set<Produkt> produkts, Sklep sklep){
        Transakcja transakcja = new Transakcja();
        transakcja.setProdukties(produkts);
        transakcja.setSklep(sklep);
        transakcja.setBrutton(RandomUtil.generateRandomDouble());
        transakcja.setNettoo(RandomUtil.generateRandomDouble());
        transakcja.setVat(RandomUtil.generateRandomNumber());

        return transakcjaRepository.save(transakcja);
    }
}
