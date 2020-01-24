import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { ServiceWorkerModule } from '@angular/service-worker';

import './vendor';
import { InformaSharedModule } from 'app/shared/shared.module';
import { InformaCoreModule } from 'app/core/core.module';
import { InformaAppRoutingModule } from './app-routing.module';
import { InformaHomeModule } from './home/home.module';
import { InformaEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ActiveMenuDirective } from './layouts/navbar/active-menu.directive';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    InformaSharedModule,
    InformaCoreModule,
    InformaHomeModule,
    ServiceWorkerModule.register('/service-worker.js', { enabled: true }),
    // jhipster-needle-angular-add-module JHipster will add new module here
    InformaEntityModule,
    InformaAppRoutingModule
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, ActiveMenuDirective, FooterComponent],
  bootstrap: [MainComponent]
})
export class InformaAppModule {}
