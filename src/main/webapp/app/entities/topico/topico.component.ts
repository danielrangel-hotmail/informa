import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITopico } from 'app/shared/model/topico.model';
import { TopicoService } from './topico.service';
import { TopicoDeleteDialogComponent } from './topico-delete-dialog.component';

@Component({
  selector: 'jhi-topico',
  templateUrl: './topico.component.html'
})
export class TopicoComponent implements OnInit, OnDestroy {
  topicos?: ITopico[];
  eventSubscriber?: Subscription;

  constructor(protected topicoService: TopicoService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.topicoService.query().subscribe((res: HttpResponse<ITopico[]>) => {
      this.topicos = res.body ? res.body : [];
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTopicos();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITopico): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTopicos(): void {
    this.eventSubscriber = this.eventManager.subscribe('topicoListModification', () => this.loadAll());
  }

  delete(topico: ITopico): void {
    const modalRef = this.modalService.open(TopicoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.topico = topico;
  }
}
