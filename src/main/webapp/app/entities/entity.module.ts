import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'transakcja',
        loadChildren: () => import('./transakcja/transakcja.module').then(m => m.OracleTransakcjaModule)
      },
      {
        path: 'produkt',
        loadChildren: () => import('./produkt/produkt.module').then(m => m.OracleProduktModule)
      },
      {
        path: 'sklep',
        loadChildren: () => import('./sklep/sklep.module').then(m => m.OracleSklepModule)
      },
      {
        path: 'godziny-otwarcia',
        loadChildren: () => import('./godziny-otwarcia/godziny-otwarcia.module').then(m => m.OracleGodzinyOtwarciaModule)
      },
      {
        path: 'osoba',
        loadChildren: () => import('./osoba/osoba.module').then(m => m.OracleOsobaModule)
      },
      {
        path: 'adres',
        loadChildren: () => import('./adres/adres.module').then(m => m.OracleAdresModule)
      },
      {
        path: 'nieruchomosc',
        loadChildren: () => import('./nieruchomosc/nieruchomosc.module').then(m => m.OracleNieruchomoscModule)
      },
      {
        path: 'mieszkanie',
        loadChildren: () => import('./mieszkanie/mieszkanie.module').then(m => m.OracleMieszkanieModule)
      },
      {
        path: 'lokalizacja',
        loadChildren: () => import('./lokalizacja/lokalizacja.module').then(m => m.OracleLokalizacjaModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class OracleEntityModule {}
