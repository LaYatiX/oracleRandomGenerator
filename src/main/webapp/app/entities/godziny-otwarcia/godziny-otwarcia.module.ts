import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { OracleSharedModule } from 'app/shared/shared.module';
import { GodzinyOtwarciaComponent } from './godziny-otwarcia.component';
import { GodzinyOtwarciaDetailComponent } from './godziny-otwarcia-detail.component';
import { GodzinyOtwarciaUpdateComponent } from './godziny-otwarcia-update.component';
import { GodzinyOtwarciaDeletePopupComponent, GodzinyOtwarciaDeleteDialogComponent } from './godziny-otwarcia-delete-dialog.component';
import { godzinyOtwarciaRoute, godzinyOtwarciaPopupRoute } from './godziny-otwarcia.route';

const ENTITY_STATES = [...godzinyOtwarciaRoute, ...godzinyOtwarciaPopupRoute];

@NgModule({
  imports: [OracleSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    GodzinyOtwarciaComponent,
    GodzinyOtwarciaDetailComponent,
    GodzinyOtwarciaUpdateComponent,
    GodzinyOtwarciaDeleteDialogComponent,
    GodzinyOtwarciaDeletePopupComponent
  ],
  entryComponents: [GodzinyOtwarciaDeleteDialogComponent]
})
export class OracleGodzinyOtwarciaModule {}
