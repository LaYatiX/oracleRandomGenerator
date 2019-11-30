import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { OracleSharedModule } from 'app/shared/shared.module';
import { AdresComponent } from './adres.component';
import { AdresDetailComponent } from './adres-detail.component';
import { AdresUpdateComponent } from './adres-update.component';
import { AdresDeletePopupComponent, AdresDeleteDialogComponent } from './adres-delete-dialog.component';
import { adresRoute, adresPopupRoute } from './adres.route';

const ENTITY_STATES = [...adresRoute, ...adresPopupRoute];

@NgModule({
  imports: [OracleSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [AdresComponent, AdresDetailComponent, AdresUpdateComponent, AdresDeleteDialogComponent, AdresDeletePopupComponent],
  entryComponents: [AdresDeleteDialogComponent]
})
export class OracleAdresModule {}
