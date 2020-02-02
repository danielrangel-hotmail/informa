import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { InformaSharedModule } from 'app/shared/shared.module';
import { PerfilGrupoComponent } from './perfil-grupo.component';
import { PerfilGrupoDetailComponent } from './perfil-grupo-detail.component';
import { PerfilGrupoUpdateComponent } from './perfil-grupo-update.component';
import { PerfilGrupoDeleteDialogComponent } from './perfil-grupo-delete-dialog.component';
import { perfilGrupoRoute } from './perfil-grupo.route';
import { GrupoNoRouteModule } from 'app/entities/grupo/grupo-no-route/grupo-no-route.module';
import { PerfilGrupoTopicoComponent } from './perfil-grupo-topico/perfil-grupo-topico.component';

@NgModule({
  imports: [InformaSharedModule, RouterModule.forChild(perfilGrupoRoute), GrupoNoRouteModule],
  declarations: [PerfilGrupoComponent, PerfilGrupoDetailComponent, PerfilGrupoUpdateComponent, PerfilGrupoDeleteDialogComponent, PerfilGrupoTopicoComponent],
  entryComponents: [PerfilGrupoDeleteDialogComponent]
})
export class InformaPerfilGrupoModule {}
