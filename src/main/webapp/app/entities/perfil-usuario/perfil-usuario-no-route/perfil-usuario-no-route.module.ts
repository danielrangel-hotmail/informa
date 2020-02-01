import { NgModule } from '@angular/core';
import { InputSimpleUserComponent } from './input-simple-user/input-simple-user.component';
import { InformaSharedModule } from 'app/shared/shared.module';



@NgModule({
  declarations: [InputSimpleUserComponent],
  exports: [
    InputSimpleUserComponent
  ],
  imports: [
    InformaSharedModule
  ]
})
export class PerfilUsuarioNoRouteModule { }
