import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { GrupoTestaComponent } from './grupo-testa/grupo-testa.component';



@NgModule({
  declarations: [GrupoTestaComponent],
  imports: [
    CommonModule
  ],
  exports: [ GrupoTestaComponent ]
})
export class GrupoNoRouteModule { }
