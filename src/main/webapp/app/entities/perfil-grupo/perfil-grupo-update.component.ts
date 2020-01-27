import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IPerfilGrupo, PerfilGrupo } from 'app/shared/model/perfil-grupo.model';
import { PerfilGrupoService } from './perfil-grupo.service';
import { IPerfilUsuario } from 'app/shared/model/perfil-usuario.model';
import { PerfilUsuarioService } from 'app/entities/perfil-usuario/perfil-usuario.service';
import { IGrupo } from 'app/shared/model/grupo.model';
import { GrupoService } from 'app/entities/grupo/grupo.service';

type SelectableEntity = IPerfilUsuario | IGrupo;

@Component({
  selector: 'jhi-perfil-grupo-update',
  templateUrl: './perfil-grupo-update.component.html'
})
export class PerfilGrupoUpdateComponent implements OnInit {
  isSaving = false;

  perfilusuarios: IPerfilUsuario[] = [];

  grupos: IGrupo[] = [];

  editForm = this.fb.group({
    id: [],
    criacao: [],
    ultimaEdicao: [],
    versao: [],
    favorito: [],
    notifica: [],
    perfilId: [null, Validators.required],
    grupoId: [null, Validators.required]
  });

  constructor(
    protected perfilGrupoService: PerfilGrupoService,
    protected perfilUsuarioService: PerfilUsuarioService,
    protected grupoService: GrupoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ perfilGrupo }) => {
      this.updateForm(perfilGrupo);

      this.perfilUsuarioService
        .query()
        .pipe(
          map((res: HttpResponse<IPerfilUsuario[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IPerfilUsuario[]) => (this.perfilusuarios = resBody));

      this.grupoService
        .query()
        .pipe(
          map((res: HttpResponse<IGrupo[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IGrupo[]) => (this.grupos = resBody));
    });
  }

  updateForm(perfilGrupo: IPerfilGrupo): void {
    this.editForm.patchValue({
      id: perfilGrupo.id,
      criacao: perfilGrupo.criacao != null ? perfilGrupo.criacao.format(DATE_TIME_FORMAT) : null,
      ultimaEdicao: perfilGrupo.ultimaEdicao != null ? perfilGrupo.ultimaEdicao.format(DATE_TIME_FORMAT) : null,
      versao: perfilGrupo.versao,
      favorito: perfilGrupo.favorito,
      notifica: perfilGrupo.notifica,
      perfilId: perfilGrupo.perfilId,
      grupoId: perfilGrupo.grupoId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const perfilGrupo = this.createFromForm();
    if (perfilGrupo.id !== undefined) {
      this.subscribeToSaveResponse(this.perfilGrupoService.update(perfilGrupo));
    } else {
      this.subscribeToSaveResponse(this.perfilGrupoService.create(perfilGrupo));
    }
  }

  private createFromForm(): IPerfilGrupo {
    return {
      ...new PerfilGrupo(),
      id: this.editForm.get(['id'])!.value,
      criacao: this.editForm.get(['criacao'])!.value != null ? moment(this.editForm.get(['criacao'])!.value, DATE_TIME_FORMAT) : undefined,
      ultimaEdicao:
        this.editForm.get(['ultimaEdicao'])!.value != null
          ? moment(this.editForm.get(['ultimaEdicao'])!.value, DATE_TIME_FORMAT)
          : undefined,
      versao: this.editForm.get(['versao'])!.value,
      favorito: this.editForm.get(['favorito'])!.value,
      notifica: this.editForm.get(['notifica'])!.value,
      perfilId: this.editForm.get(['perfilId'])!.value,
      grupoId: this.editForm.get(['grupoId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPerfilGrupo>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
