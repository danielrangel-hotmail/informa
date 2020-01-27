import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { InformaSharedModule } from 'app/shared/shared.module';
import { PerfilGrupoComponent } from './perfil-grupo.component';
import { PerfilGrupoDetailComponent } from './perfil-grupo-detail.component';
import { PerfilGrupoUpdateComponent } from './perfil-grupo-update.component';
import { PerfilGrupoDeleteDialogComponent } from './perfil-grupo-delete-dialog.component';
import { perfilGrupoRoute } from './perfil-grupo.route';

@NgModule({
  imports: [InformaSharedModule, RouterModule.forChild(perfilGrupoRoute)],
  declarations: [PerfilGrupoComponent, PerfilGrupoDetailComponent, PerfilGrupoUpdateComponent, PerfilGrupoDeleteDialogComponent],
  entryComponents: [PerfilGrupoDeleteDialogComponent]
})
export class InformaPerfilGrupoModule {}
