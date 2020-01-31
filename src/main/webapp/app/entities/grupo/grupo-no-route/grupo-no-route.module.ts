import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { GrupoTestaComponent } from './grupo-testa/grupo-testa.component';
import { GrupoLogoComponent } from './grupo-logo/grupo-logo.component';



@NgModule({
  declarations: [GrupoTestaComponent, GrupoLogoComponent],
  imports: [
    CommonModule
  ],
  exports: [ GrupoTestaComponent, GrupoLogoComponent ]
})
export class GrupoNoRouteModule { }
