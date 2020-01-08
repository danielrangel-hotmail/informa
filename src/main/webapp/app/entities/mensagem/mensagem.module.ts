import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { InformaSharedModule } from 'app/shared/shared.module';
import { MensagemComponent } from './mensagem.component';
import { MensagemDetailComponent } from './mensagem-detail.component';
import { MensagemUpdateComponent } from './mensagem-update.component';
import { MensagemDeleteDialogComponent } from './mensagem-delete-dialog.component';
import { mensagemRoute } from './mensagem.route';

@NgModule({
  imports: [InformaSharedModule, RouterModule.forChild(mensagemRoute)],
  declarations: [MensagemComponent, MensagemDetailComponent, MensagemUpdateComponent, MensagemDeleteDialogComponent],
  entryComponents: [MensagemDeleteDialogComponent]
})
export class InformaMensagemModule {}
