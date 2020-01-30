import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { InformaSharedModule } from 'app/shared/shared.module';
import { TopicoComponent } from './topico.component';
import { TopicoDetailComponent } from './topico-detail.component';
import { TopicoUpdateComponent } from './topico-update.component';
import { TopicoDeleteDialogComponent } from './topico-delete-dialog.component';
import { topicoRoute } from './topico.route';

@NgModule({
  imports: [InformaSharedModule, RouterModule.forChild(topicoRoute)],
  declarations: [TopicoComponent, TopicoDetailComponent, TopicoUpdateComponent, TopicoDeleteDialogComponent],
  entryComponents: [TopicoDeleteDialogComponent]
})
export class InformaTopicoModule {}
