import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPostReacao } from 'app/shared/model/post-reacao.model';
import { PostReacaoService } from './post-reacao.service';
import { PostReacaoDeleteDialogComponent } from './post-reacao-delete-dialog.component';

@Component({
  selector: 'jhi-post-reacao',
  templateUrl: './post-reacao.component.html'
})
export class PostReacaoComponent implements OnInit, OnDestroy {
  postReacaos?: IPostReacao[];
  eventSubscriber?: Subscription;

  constructor(protected postReacaoService: PostReacaoService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.postReacaoService.query().subscribe((res: HttpResponse<IPostReacao[]>) => {
      this.postReacaos = res.body ? res.body : [];
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPostReacaos();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPostReacao): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInPostReacaos(): void {
    this.eventSubscriber = this.eventManager.subscribe('postReacaoListModification', () => this.loadAll());
  }

  delete(postReacao: IPostReacao): void {
    const modalRef = this.modalService.open(PostReacaoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.postReacao = postReacao;
  }
}
