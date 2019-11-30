import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { OracleSharedModule } from 'app/shared/shared.module';
import { TransakcjaComponent } from './transakcja.component';
import { TransakcjaDetailComponent } from './transakcja-detail.component';
import { TransakcjaUpdateComponent } from './transakcja-update.component';
import { TransakcjaDeletePopupComponent, TransakcjaDeleteDialogComponent } from './transakcja-delete-dialog.component';
import { transakcjaRoute, transakcjaPopupRoute } from './transakcja.route';

const ENTITY_STATES = [...transakcjaRoute, ...transakcjaPopupRoute];

@NgModule({
  imports: [OracleSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    TransakcjaComponent,
    TransakcjaDetailComponent,
    TransakcjaUpdateComponent,
    TransakcjaDeleteDialogComponent,
    TransakcjaDeletePopupComponent
  ],
  entryComponents: [TransakcjaDeleteDialogComponent]
})
export class OracleTransakcjaModule {}
