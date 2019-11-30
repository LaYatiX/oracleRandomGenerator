import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { OracleSharedModule } from 'app/shared/shared.module';
import { LokalizacjaComponent } from './lokalizacja.component';
import { LokalizacjaDetailComponent } from './lokalizacja-detail.component';
import { LokalizacjaUpdateComponent } from './lokalizacja-update.component';
import { LokalizacjaDeletePopupComponent, LokalizacjaDeleteDialogComponent } from './lokalizacja-delete-dialog.component';
import { lokalizacjaRoute, lokalizacjaPopupRoute } from './lokalizacja.route';

const ENTITY_STATES = [...lokalizacjaRoute, ...lokalizacjaPopupRoute];

@NgModule({
  imports: [OracleSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    LokalizacjaComponent,
    LokalizacjaDetailComponent,
    LokalizacjaUpdateComponent,
    LokalizacjaDeleteDialogComponent,
    LokalizacjaDeletePopupComponent
  ],
  entryComponents: [LokalizacjaDeleteDialogComponent]
})
export class OracleLokalizacjaModule {}
