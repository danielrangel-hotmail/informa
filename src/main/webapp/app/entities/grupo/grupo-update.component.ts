import { AfterViewInit, Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IGrupo, Grupo } from 'app/shared/model/grupo.model';
import { GrupoService } from './grupo.service';
import { PerfilGrupoViewService } from 'app/layouts/navbar/perfil-grupo-view.service';

@Component({
  selector: 'jhi-grupo-update',
  templateUrl: './grupo-update.component.html',
  styleUrls: [
    './grupo-update.component.scss'
  ]
})
export class GrupoUpdateComponent implements OnInit, AfterViewInit{
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    versao: [],
    criacao: [],
    ultimaEdicao: [],
    nome: [],
    descricao: [],
    formal: [],
    opcional: []
  });

  constructor(
    protected grupoService: GrupoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    protected perfilGrupoViewService: PerfilGrupoViewService) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ grupo }) => {
      this.updateForm(grupo);
    });
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
      opcional: grupo.opcional
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
      opcional: this.editForm.get(['opcional'])!.value
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
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  ngAfterViewInit(): void {
    this.perfilGrupoViewService.navega(this.editForm.get(['id'])!.value ? "editando grupo" : "criando grupo");
  }
}
