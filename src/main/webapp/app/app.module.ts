import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { OracleSharedModule } from 'app/shared/shared.module';
import { OracleCoreModule } from 'app/core/core.module';
import { OracleAppRoutingModule } from './app-routing.module';
import { OracleHomeModule } from './home/home.module';
import { OracleEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { JhiMainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    OracleSharedModule,
    OracleCoreModule,
    OracleHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    OracleEntityModule,
    OracleAppRoutingModule
  ],
  declarations: [JhiMainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [JhiMainComponent]
})
export class OracleAppModule {}
