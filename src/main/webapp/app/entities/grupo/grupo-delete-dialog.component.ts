import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { GrupoService } from './grupo.service';
import { IGrupo } from '../../shared/model/grupo.interface';

@Component({
  templateUrl: './grupo-delete-dialog.component.html'
})
export class GrupoDeleteDialogComponent {
  grupo?: IGrupo;

  constructor(protected grupoService: GrupoService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.grupoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('grupoListModification');
      this.activeModal.close();
    });
  }
}
