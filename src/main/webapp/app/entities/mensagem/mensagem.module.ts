import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { QuillModule } from 'ngx-quill';
import { InformaSharedModule } from 'app/shared/shared.module';
import { MensagemComponent } from './mensagem.component';
import { MensagemDetailComponent } from './mensagem-detail.component';
import { MensagemUpdateComponent } from './mensagem-update.component';
import { MensagemDeleteDialogComponent } from './mensagem-delete-dialog.component';
import { GravatarModule } from  'ngx-gravatar';
import { mensagemRoute } from './mensagem.route';
import { MensagemPostComponent } from './mensagem-post/mensagem-post.component';
import {InformaSharedPostModule} from 'app/entities/shared-post/informa-shared-post.module';
import { MensagemPopupMenuComponent } from './mensagem-popup-menu/mensagem-popup-menu.component';

@NgModule({
  imports: [InformaSharedModule, InformaSharedPostModule, RouterModule.forChild(mensagemRoute), GravatarModule, QuillModule],
  declarations: [MensagemComponent, MensagemDetailComponent, MensagemUpdateComponent, MensagemDeleteDialogComponent, MensagemPostComponent, MensagemPopupMenuComponent],
  entryComponents: [MensagemDeleteDialogComponent]
})
export class InformaMensagemModule {}
