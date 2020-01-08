import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { InformaSharedModule } from 'app/shared/shared.module';
import { GrupoComponent } from './grupo.component';
import { GrupoDetailComponent } from './grupo-detail.component';
import { GrupoUpdateComponent } from './grupo-update.component';
import { GrupoDeleteDialogComponent } from './grupo-delete-dialog.component';
import { grupoRoute } from './grupo.route';

@NgModule({
  imports: [InformaSharedModule, RouterModule.forChild(grupoRoute)],
  declarations: [GrupoComponent, GrupoDetailComponent, GrupoUpdateComponent, GrupoDeleteDialogComponent],
  entryComponents: [GrupoDeleteDialogComponent]
})
export class InformaGrupoModule {}
