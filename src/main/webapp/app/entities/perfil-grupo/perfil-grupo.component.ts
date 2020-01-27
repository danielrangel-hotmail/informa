import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Observable, Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPerfilGrupo, PerfilGrupo } from 'app/shared/model/perfil-grupo.model';

import { PerfilGrupoService } from './perfil-grupo.service';
import { PerfilGrupoDeleteDialogComponent } from './perfil-grupo-delete-dialog.component';
import { IGrupo } from 'app/shared/model/grupo.model';

@Component({
  selector: 'jhi-perfil-grupo',
  templateUrl: './perfil-grupo.component.html',
  styleUrls: [ './perfil-grupo.component.scss']
})
export class PerfilGrupoComponent implements OnInit, OnDestroy {
  perfilGrupos: IPerfilGrupo[];
  eventSubscriber?: Subscription;
  predicate: string;
  ascending: boolean;
  isSaving = false;

  constructor(
    protected perfilGrupoService: PerfilGrupoService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.perfilGrupos = [];
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.perfilGrupoService
      .queryManagement()
      .subscribe((res: HttpResponse<IPerfilGrupo[]>) => this.perfilGrupos = this.perfilGrupoService.perfisOrdenados(res.body ? res.body : []));
  }

  reset(): void {
    this.perfilGrupos = [];
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPerfilGrupos();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPerfilGrupo): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return index;
  }

  registerChangeInPerfilGrupos(): void {
    this.eventSubscriber = this.eventManager.subscribe('perfilGrupoListModification', () => this.reset());
  }

  delete(perfilGrupo: IPerfilGrupo): void {
    const modalRef = this.modalService.open(PerfilGrupoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.perfilGrupo = perfilGrupo;
  }

  entra(perfilGrupo: IPerfilGrupo): void {
      this.perfilGrupoService.create(perfilGrupo).subscribe(
        (res) => this.onSaveSuccess(res.body!),
        () => this.onSaveError()
      );
  }

  sai(perfilGrupo: IPerfilGrupo): void {
    if (perfilGrupo.id === undefined) return;
    this.perfilGrupoService.delete(perfilGrupo.id).subscribe(
      (res) => this.onDeleteSuccess(perfilGrupo.grupo!),
      () => this.onSaveError()
    );
  }

  protected onDeleteSuccess(grupo: IGrupo): void {
    const index = this.perfilGrupos.findIndex((each) => each.grupo!.id === grupo.id);
    const novoPerfilGrupo = new PerfilGrupo();
    novoPerfilGrupo.grupo = grupo;
    this.perfilGrupos[index] =  novoPerfilGrupo;
    this.isSaving = false;
    this.eventManager.broadcast('perfilGrupoListModification');
  }

  protected onSaveSuccess(perfilGrupo: IPerfilGrupo): void {
    const index = this.perfilGrupos.findIndex((each) => each.grupo!.id === perfilGrupo.grupo!.id);
    this.perfilGrupos[index] = perfilGrupo;
    this.isSaving = false;
    this.eventManager.broadcast('perfilGrupoListModification');
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
