import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITopico } from 'app/shared/model/topico.model';
import { TopicoService } from './topico.service';

@Component({
  templateUrl: './topico-delete-dialog.component.html'
})
export class TopicoDeleteDialogComponent {
  topico?: ITopico;

  constructor(protected topicoService: TopicoService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.topicoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('topicoListModification');
      this.activeModal.close();
    });
  }
}
