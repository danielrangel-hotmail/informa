import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPerfilGrupo } from 'app/shared/model/perfil-grupo.model';

import { PerfilGrupoService } from 'app/entities/perfil-grupo/perfil-grupo.service';

@Component({
  selector: 'jhi-perfil-grupo-view',
  templateUrl: './perfil-grupo-view.component.html',
  styleUrls: [ './perfil-grupo-view.component.scss']
})
export class PerfilGrupoViewComponent implements OnInit, OnDestroy {
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
      .subscribe((res: HttpResponse<IPerfilGrupo[]>) => this.perfilGrupos = this.perfilGrupoService.perfisOrdenadosEExistentes(res.body ? res.body : []));
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

  protected onSaveSuccess(perfilGrupo: IPerfilGrupo): void {
    const index = this.perfilGrupos.findIndex((each) => each.id === perfilGrupo.id);
    this.perfilGrupos[index] = perfilGrupo;
    this.isSaving = false;
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  public toggleNotifica(perfilGrupo: IPerfilGrupo): void {
    const perfilGrupoUpdated = { ...perfilGrupo, notifica: !perfilGrupo.notifica };
    this.update(perfilGrupoUpdated);
  }

  protected update(perfilGrupo: IPerfilGrupo): void {
    this.perfilGrupoService.update(perfilGrupo).subscribe(
      (res) => this.onSaveSuccess(res.body!),
      () => this.onSaveError()
    );
  }
  public toggleFavorito(perfilGrupo: IPerfilGrupo): void {
    const perfilGrupoUpdated = { ...perfilGrupo, favorito: !perfilGrupo.favorito };
    this.update(perfilGrupoUpdated);
  }

  perfilGruposFavoritos(): IPerfilGrupo[] {
    return this.perfilGrupos.filter(perfil => perfil.favorito);
  }
}
