import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { QuillModule } from 'ngx-quill';
import { InformaSharedModule } from 'app/shared/shared.module';
import { MensagemComponent } from './mensagem.component';
import { MensagemDetailComponent } from './mensagem-detail.component';
import { MensagemUpdateComponent } from './mensagem-update.component';
import { MensagemDeleteDialogComponent } from './mensagem-delete-dialog.component';
import { GravatarModule } from  'ngx-gravatar';

@NgModule({
  imports: [InformaSharedModule, RouterModule, GravatarModule, QuillModule],
  declarations: [MensagemComponent, MensagemDetailComponent, MensagemUpdateComponent, MensagemDeleteDialogComponent],
  exports: [MensagemComponent, MensagemDetailComponent, MensagemUpdateComponent, MensagemDeleteDialogComponent],
  entryComponents: [MensagemDeleteDialogComponent]
})
export class InformaMensagemModule {}
