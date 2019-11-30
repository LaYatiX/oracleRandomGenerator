import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { OracleSharedModule } from 'app/shared/shared.module';
import { MieszkanieComponent } from './mieszkanie.component';
import { MieszkanieDetailComponent } from './mieszkanie-detail.component';
import { MieszkanieUpdateComponent } from './mieszkanie-update.component';
import { MieszkanieDeletePopupComponent, MieszkanieDeleteDialogComponent } from './mieszkanie-delete-dialog.component';
import { mieszkanieRoute, mieszkaniePopupRoute } from './mieszkanie.route';

const ENTITY_STATES = [...mieszkanieRoute, ...mieszkaniePopupRoute];

@NgModule({
  imports: [OracleSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    MieszkanieComponent,
    MieszkanieDetailComponent,
    MieszkanieUpdateComponent,
    MieszkanieDeleteDialogComponent,
    MieszkanieDeletePopupComponent
  ],
  entryComponents: [MieszkanieDeleteDialogComponent]
})
export class OracleMieszkanieModule {}
