import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { OracleSharedModule } from 'app/shared/shared.module';
import { SklepComponent } from './sklep.component';
import { SklepDetailComponent } from './sklep-detail.component';
import { SklepUpdateComponent } from './sklep-update.component';
import { SklepDeletePopupComponent, SklepDeleteDialogComponent } from './sklep-delete-dialog.component';
import { sklepRoute, sklepPopupRoute } from './sklep.route';

const ENTITY_STATES = [...sklepRoute, ...sklepPopupRoute];

@NgModule({
  imports: [OracleSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [SklepComponent, SklepDetailComponent, SklepUpdateComponent, SklepDeleteDialogComponent, SklepDeletePopupComponent],
  entryComponents: [SklepDeleteDialogComponent]
})
export class OracleSklepModule {}
