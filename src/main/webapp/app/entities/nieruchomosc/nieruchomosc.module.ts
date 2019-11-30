import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { OracleSharedModule } from 'app/shared/shared.module';
import { NieruchomoscComponent } from './nieruchomosc.component';
import { NieruchomoscDetailComponent } from './nieruchomosc-detail.component';
import { NieruchomoscUpdateComponent } from './nieruchomosc-update.component';
import { NieruchomoscDeletePopupComponent, NieruchomoscDeleteDialogComponent } from './nieruchomosc-delete-dialog.component';
import { nieruchomoscRoute, nieruchomoscPopupRoute } from './nieruchomosc.route';

const ENTITY_STATES = [...nieruchomoscRoute, ...nieruchomoscPopupRoute];

@NgModule({
  imports: [OracleSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    NieruchomoscComponent,
    NieruchomoscDetailComponent,
    NieruchomoscUpdateComponent,
    NieruchomoscDeleteDialogComponent,
    NieruchomoscDeletePopupComponent
  ],
  entryComponents: [NieruchomoscDeleteDialogComponent]
})
export class OracleNieruchomoscModule {}
