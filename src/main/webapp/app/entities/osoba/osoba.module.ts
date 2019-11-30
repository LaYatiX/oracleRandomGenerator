import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { OracleSharedModule } from 'app/shared/shared.module';
import { OsobaComponent } from './osoba.component';
import { OsobaDetailComponent } from './osoba-detail.component';
import { OsobaUpdateComponent } from './osoba-update.component';
import { OsobaDeletePopupComponent, OsobaDeleteDialogComponent } from './osoba-delete-dialog.component';
import { osobaRoute, osobaPopupRoute } from './osoba.route';

const ENTITY_STATES = [...osobaRoute, ...osobaPopupRoute];

@NgModule({
  imports: [OracleSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [OsobaComponent, OsobaDetailComponent, OsobaUpdateComponent, OsobaDeleteDialogComponent, OsobaDeletePopupComponent],
  entryComponents: [OsobaDeleteDialogComponent]
})
export class OracleOsobaModule {}
