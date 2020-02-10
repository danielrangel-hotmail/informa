import { Component, OnInit, OnDestroy, Input } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { PerfilGrupoService } from '../../entities/perfil-grupo/perfil-grupo.service';
import { AccountService } from '../../core/auth/account.service';
import { FormControl } from '@angular/forms';
import { debounceTime } from 'rxjs/operators';
import { DOCKED, PerfilGrupoViewService } from '../../layouts/navbar/perfil-grupo-view.service';
import { IPerfilGrupo } from '../../shared/model/perfil-grupo.interface';
import { Router } from '@angular/router';


@Component({
  selector: 'jhi-perfil-grupo-view',
  templateUrl: './perfil-grupo-view.component.html',
  styleUrls: [ './perfil-grupo-view.component.scss']
})
export class PerfilGrupoViewComponent implements OnInit, OnDestroy {
  @Input() dockingState!: string;
  perfilGrupos: IPerfilGrupo[];
  eventSubscriber?: Subscription;
  predicate: string;
  ascending: boolean;
  isSaving = false;
  filtro = new FormControl();
  filtroValue = '';
  DOCKED = DOCKED;


  constructor(
    protected perfilGrupoService: PerfilGrupoService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks,
    protected accountService: AccountService,
    protected router: Router,
    public perfilGrupoViewService: PerfilGrupoViewService
  ) {
    this.perfilGrupos = [];
    this.predicate = 'id';
    this.ascending = true;
    this.filtro.valueChanges
      .pipe(debounceTime(200))
      .subscribe(val => {
        this.filtroValue = val;
      });
  }

  filtroMatch(valor: string): boolean {
    return (this.filtroValue === '') || (RegExp(this.filtroValue).exec(valor) !== null);
  }

  isAuthenticated(): boolean {
    return this.accountService.isAuthenticated();
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

  trackId(index: number): number {
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

  public toggleModerador(perfilGrupo: IPerfilGrupo): void {
    this.router.navigateByUrl(`/grupo/${perfilGrupo.grupo!.id}/edit`);
  }

  perfilGruposFavoritos(): IPerfilGrupo[] {
    return this.perfilGrupos.filter(perfil => perfil.favorito || perfil.moderador);
  }
}
