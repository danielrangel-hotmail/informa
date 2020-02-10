import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { MensagemService } from './mensagem.service';
import {IMensagem} from '../../shared/model/mensagem.interface';

@Component({
  templateUrl: './mensagem-delete-dialog.component.html'
})
export class MensagemDeleteDialogComponent {
  mensagem?: IMensagem;

  constructor(protected mensagemService: MensagemService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.mensagemService.delete(id).subscribe(() => {
      this.eventManager.broadcast('mensagemListModification');
      this.activeModal.close();
    });
  }
}
