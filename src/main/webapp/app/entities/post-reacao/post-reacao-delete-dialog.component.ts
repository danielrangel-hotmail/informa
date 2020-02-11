import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPostReacao } from 'app/shared/model/post-reacao.model';
import { PostReacaoService } from './post-reacao.service';

@Component({
  templateUrl: './post-reacao-delete-dialog.component.html'
})
export class PostReacaoDeleteDialogComponent {
  postReacao?: IPostReacao;

  constructor(
    protected postReacaoService: PostReacaoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.postReacaoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('postReacaoListModification');
      this.activeModal.close();
    });
  }
}
