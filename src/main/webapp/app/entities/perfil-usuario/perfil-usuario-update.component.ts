import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IPerfilUsuario, PerfilUsuario } from 'app/shared/model/perfil-usuario.model';
import { PerfilUsuarioService } from './perfil-usuario.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'jhi-perfil-usuario-update',
  templateUrl: './perfil-usuario-update.component.html'
})
export class PerfilUsuarioUpdateComponent implements OnInit {
  isSaving = false;

  users: IUser[] = [];
  entradaNaEmpresaDp: any;
  saidaDaEmpresaDp: any;
  nascimentoDp: any;

  editForm = this.fb.group({
    id: [],
    criacao: [],
    ultimaEdicao: [],
    versao: [],
    entradaNaEmpresa: [],
    saidaDaEmpresa: [],
    nascimento: [],
    skype: [],
    usuarioId: [null, Validators.required]
  });

  constructor(
    protected perfilUsuarioService: PerfilUsuarioService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ perfilUsuario }) => {
      this.updateForm(perfilUsuario);

      this.userService
        .query()
        .pipe(
          map((res: HttpResponse<IUser[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IUser[]) => (this.users = resBody));
    });
  }

  updateForm(perfilUsuario: IPerfilUsuario): void {
    this.editForm.patchValue({
      id: perfilUsuario.id,
      criacao: perfilUsuario.criacao != null ? perfilUsuario.criacao.format(DATE_TIME_FORMAT) : null,
      ultimaEdicao: perfilUsuario.ultimaEdicao != null ? perfilUsuario.ultimaEdicao.format(DATE_TIME_FORMAT) : null,
      versao: perfilUsuario.versao,
      entradaNaEmpresa: perfilUsuario.entradaNaEmpresa,
      saidaDaEmpresa: perfilUsuario.saidaDaEmpresa,
      nascimento: perfilUsuario.nascimento,
      skype: perfilUsuario.skype,
      usuarioId: perfilUsuario.usuarioId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const perfilUsuario = this.createFromForm();
    if (perfilUsuario.id !== undefined) {
      this.subscribeToSaveResponse(this.perfilUsuarioService.update(perfilUsuario));
    } else {
      this.subscribeToSaveResponse(this.perfilUsuarioService.create(perfilUsuario));
    }
  }

  private createFromForm(): IPerfilUsuario {
    return {
      ...new PerfilUsuario(),
      id: this.editForm.get(['id'])!.value,
      criacao: this.editForm.get(['criacao'])!.value != null ? moment(this.editForm.get(['criacao'])!.value, DATE_TIME_FORMAT) : undefined,
      ultimaEdicao:
        this.editForm.get(['ultimaEdicao'])!.value != null
          ? moment(this.editForm.get(['ultimaEdicao'])!.value, DATE_TIME_FORMAT)
          : undefined,
      versao: this.editForm.get(['versao'])!.value,
      entradaNaEmpresa: this.editForm.get(['entradaNaEmpresa'])!.value,
      saidaDaEmpresa: this.editForm.get(['saidaDaEmpresa'])!.value,
      nascimento: this.editForm.get(['nascimento'])!.value,
      skype: this.editForm.get(['skype'])!.value,
      usuarioId: this.editForm.get(['usuarioId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPerfilUsuario>>): void {
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

  trackById(index: number, item: IUser): any {
    return item.id;
  }
}
