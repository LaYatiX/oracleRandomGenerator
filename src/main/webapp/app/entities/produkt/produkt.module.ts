import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { OracleSharedModule } from 'app/shared/shared.module';
import { ProduktComponent } from './produkt.component';
import { ProduktDetailComponent } from './produkt-detail.component';
import { ProduktUpdateComponent } from './produkt-update.component';
import { ProduktDeletePopupComponent, ProduktDeleteDialogComponent } from './produkt-delete-dialog.component';
import { produktRoute, produktPopupRoute } from './produkt.route';

const ENTITY_STATES = [...produktRoute, ...produktPopupRoute];

@NgModule({
  imports: [OracleSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ProduktComponent,
    ProduktDetailComponent,
    ProduktUpdateComponent,
    ProduktDeleteDialogComponent,
    ProduktDeletePopupComponent
  ],
  entryComponents: [ProduktDeleteDialogComponent]
})
export class OracleProduktModule {}
