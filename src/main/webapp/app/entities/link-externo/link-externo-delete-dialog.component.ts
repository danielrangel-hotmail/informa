import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILinkExterno } from 'app/shared/model/link-externo.model';
import { LinkExternoService } from './link-externo.service';

@Component({
  templateUrl: './link-externo-delete-dialog.component.html'
})
export class LinkExternoDeleteDialogComponent {
  linkExterno?: ILinkExterno;

  constructor(
    protected linkExternoService: LinkExternoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.linkExternoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('linkExternoListModification');
      this.activeModal.close();
    });
  }
}
