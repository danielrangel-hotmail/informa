import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IPerfilUsuario, PerfilUsuario } from 'app/shared/model/perfil-usuario.model';
import { PerfilUsuarioService } from './perfil-usuario.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
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
    avatar: [],
    avatarContentType: [],
    usuarioId: [null, Validators.required]
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected perfilUsuarioService: PerfilUsuarioService,
    protected userService: UserService,
    protected elementRef: ElementRef,
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
      avatar: perfilUsuario.avatar,
      avatarContentType: perfilUsuario.avatarContentType,
      usuarioId: perfilUsuario.usuarioId
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('informaApp.error', { ...err, key: 'error.file.' + err.key })
      );
    });
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null
    });
    if (this.elementRef && idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
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
      avatarContentType: this.editForm.get(['avatarContentType'])!.value,
      avatar: this.editForm.get(['avatar'])!.value,
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
