import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { InformaSharedModule } from 'app/shared/shared.module';
import { PerfilUsuarioComponent } from './perfil-usuario.component';
import { PerfilUsuarioDetailComponent } from './perfil-usuario-detail.component';
import { PerfilUsuarioUpdateComponent } from './perfil-usuario-update.component';
import { PerfilUsuarioDeleteDialogComponent } from './perfil-usuario-delete-dialog.component';
import { perfilUsuarioRoute } from './perfil-usuario.route';

@NgModule({
  imports: [InformaSharedModule, RouterModule.forChild(perfilUsuarioRoute)],
  declarations: [PerfilUsuarioComponent, PerfilUsuarioDetailComponent, PerfilUsuarioUpdateComponent, PerfilUsuarioDeleteDialogComponent],
  entryComponents: [PerfilUsuarioDeleteDialogComponent]
})
export class InformaPerfilUsuarioModule {}
