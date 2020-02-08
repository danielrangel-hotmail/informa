import { AfterViewInit, Component, ElementRef, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map, startWith, tap } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiDataUtils, JhiEventManager } from 'ng-jhipster';

import { Grupo } from 'app/shared/model/grupo.model';
import { GrupoService } from './grupo.service';
import { ITopico } from 'app/shared/model/topico.model';
import { TopicoService } from 'app/entities/topico/topico.service';
import { PerfilGrupoViewService } from 'app/layouts/navbar/perfil-grupo-view.service';
import { IGrupo } from 'app/shared/model/grupo.interface';
import { IImageCroped } from 'app/shared/avatar-cropped/avatar-cropped.component';
import { IUser } from 'app/core/user/user.model';
import { IPerfilUsuario } from 'app/shared/model/perfil-usuario.model';
import { PerfilUsuarioService } from 'app/entities/perfil-usuario/perfil-usuario.service';

@Component({
  selector: 'jhi-grupo-update',
  templateUrl: './grupo-update.component.html',
  styleUrls: [
    './grupo-update.component.scss'
  ]
})
export class GrupoUpdateComponent implements OnInit, AfterViewInit{
  isSaving = false;
  grupo$!: Observable<IGrupo>;

  topicos: ITopico[] = [];
  // usuarios: IPerfilUsuario = [];

  editForm = this.fb.group({
    id: [],
    versao: [],
    criacao: [],
    ultimaEdicao: [],
    nome: [],
    descricao: [],
    formal: [],
    opcional: [],
    logo: [],
    logoContentType: [],
    cabecalhoSuperiorCor: [],
    cabecalhoInferiorCor: [],
    logoFundoCor: [],
    topicos: [],
    moderadores: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected grupoService: GrupoService,
    protected topicoService: TopicoService,
    protected perfilUsuarioService: PerfilUsuarioService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    protected perfilGrupoViewService: PerfilGrupoViewService) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ grupo }) => {
      if (grupo === null) return;
      this.updateForm(grupo);
      this.carregaTopicos();
      this.grupo$ = this.editForm.valueChanges.pipe(
        map(() => this.createFromForm()),
        // eslint-disable-next-line no-console
        tap(() => { console.log("form changed") }),
        startWith(grupo),
      );
    });
  }

  private carregaTopicos(): void {
    this.topicoService
      .query()
      .pipe(
        map((res: HttpResponse<ITopico[]>) => {
          return res.body ? res.body : [];
        })
      )
      .subscribe((resBody: ITopico[]) => (this.topicos = resBody));
  }

  updateForm(grupo: IGrupo): void {
    this.editForm.patchValue({
      id: grupo.id,
      versao: grupo.versao,
      criacao: grupo.criacao != null ? grupo.criacao.format(DATE_TIME_FORMAT) : null,
      ultimaEdicao: grupo.ultimaEdicao != null ? grupo.ultimaEdicao.format(DATE_TIME_FORMAT) : null,
      nome: grupo.nome,
      descricao: grupo.descricao,
      formal: grupo.formal,
      opcional: grupo.opcional,
      logo: grupo.logo,
      logoContentType: grupo.logoContentType,
      cabecalhoSuperiorCor: grupo.cabecalhoSuperiorCor,
      cabecalhoInferiorCor: grupo.cabecalhoInferiorCor,
      logoFundoCor: grupo.logoFundoCor,
      topicos: grupo.topicos,
      moderadores: grupo.moderadores
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const grupo = this.createFromForm();
    if (grupo.id !== undefined) {
      this.subscribeToSaveResponse(this.grupoService.update(grupo));
    } else {
      this.subscribeToSaveResponse(this.grupoService.create(grupo));
    }
  }

  private createFromForm(): IGrupo {
    return {
      ...new Grupo(),
      id: this.editForm.get(['id'])!.value,
      versao: this.editForm.get(['versao'])!.value,
      criacao: this.editForm.get(['criacao'])!.value != null ? moment(this.editForm.get(['criacao'])!.value, DATE_TIME_FORMAT) : undefined,
      ultimaEdicao:
        this.editForm.get(['ultimaEdicao'])!.value != null
          ? moment(this.editForm.get(['ultimaEdicao'])!.value, DATE_TIME_FORMAT)
          : undefined,
      nome: this.editForm.get(['nome'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
      formal: this.editForm.get(['formal'])!.value,
      opcional: this.editForm.get(['opcional'])!.value,
      logoContentType: this.editForm.get(['logoContentType'])!.value,
      logo: this.editForm.get(['logo'])!.value,
      cabecalhoSuperiorCor: this.editForm.get(['cabecalhoSuperiorCor'])!.value,
      cabecalhoInferiorCor: this.editForm.get(['cabecalhoInferiorCor'])!.value,
      logoFundoCor: this.editForm.get(['logoFundoCor'])!.value,
      topicos: this.editForm.get(['topicos'])!.value,
      moderadores: this.editForm.get(['moderadores'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGrupo>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.eventManager.broadcast('perfilGrupoListModification');
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  ngAfterViewInit(): void {
    // this.perfilGrupoViewService.navega(this.editForm.get(['id'])!.value ? "editando grupo" : "criando grupo");
  }

  trackById(index: number, item: ITopico): any {
    return item.id;
  }

  croppedLogoChanged($event: IImageCroped): void {
    this.editForm.patchValue({
      logo: $event.content,
      logoContentType: $event.contentType,
    });
  }

  getSelected(selectedVals: ITopico[], option: ITopico): ITopico {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }

  addTopico(nome: string): ITopico {
    return {
      id: undefined,
      nome
    }
  }
}
