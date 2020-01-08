import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IGrupo } from 'app/shared/model/grupo.model';
import { GrupoService } from './grupo.service';
import { GrupoDeleteDialogComponent } from './grupo-delete-dialog.component';

@Component({
  selector: 'jhi-grupo',
  templateUrl: './grupo.component.html'
})
export class GrupoComponent implements OnInit, OnDestroy {
  grupos?: IGrupo[];
  eventSubscriber?: Subscription;

  constructor(protected grupoService: GrupoService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.grupoService.query().subscribe((res: HttpResponse<IGrupo[]>) => {
      this.grupos = res.body ? res.body : [];
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInGrupos();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IGrupo): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInGrupos(): void {
    this.eventSubscriber = this.eventManager.subscribe('grupoListModification', () => this.loadAll());
  }

  delete(grupo: IGrupo): void {
    const modalRef = this.modalService.open(GrupoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.grupo = grupo;
  }
}
