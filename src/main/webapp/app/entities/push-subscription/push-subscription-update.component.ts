import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IPushSubscription, PushSubscription } from 'app/shared/model/push-subscription.model';
import { PushSubscriptionService } from './push-subscription.service';
import { IPerfilUsuario } from 'app/shared/model/perfil-usuario.model';
import { PerfilUsuarioService } from 'app/entities/perfil-usuario/perfil-usuario.service';

@Component({
  selector: 'jhi-push-subscription-update',
  templateUrl: './push-subscription-update.component.html'
})
export class PushSubscriptionUpdateComponent implements OnInit {
  isSaving = false;

  perfilusuarios: IPerfilUsuario[] = [];

  editForm = this.fb.group({
    id: [],
    versao: [],
    criacao: [],
    endpoint: [],
    key: [],
    auth: [],
    perfilId: [null, Validators.required]
  });

  constructor(
    protected pushSubscriptionService: PushSubscriptionService,
    protected perfilUsuarioService: PerfilUsuarioService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pushSubscription }) => {
      this.updateForm(pushSubscription);

      this.perfilUsuarioService
        .query()
        .pipe(
          map((res: HttpResponse<IPerfilUsuario[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IPerfilUsuario[]) => (this.perfilusuarios = resBody));
    });
  }

  updateForm(pushSubscription: IPushSubscription): void {
    this.editForm.patchValue({
      id: pushSubscription.id,
      versao: pushSubscription.versao,
      criacao: pushSubscription.criacao != null ? pushSubscription.criacao.format(DATE_TIME_FORMAT) : null,
      endpoint: pushSubscription.endpoint,
      key: pushSubscription.key,
      auth: pushSubscription.auth,
      perfilId: pushSubscription.perfilId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const pushSubscription = this.createFromForm();
    if (pushSubscription.id !== undefined) {
      this.subscribeToSaveResponse(this.pushSubscriptionService.update(pushSubscription));
    } else {
      this.subscribeToSaveResponse(this.pushSubscriptionService.create(pushSubscription));
    }
  }

  private createFromForm(): IPushSubscription {
    return {
      ...new PushSubscription(),
      id: this.editForm.get(['id'])!.value,
      versao: this.editForm.get(['versao'])!.value,
      criacao: this.editForm.get(['criacao'])!.value != null ? moment(this.editForm.get(['criacao'])!.value, DATE_TIME_FORMAT) : undefined,
      endpoint: this.editForm.get(['endpoint'])!.value,
      key: this.editForm.get(['key'])!.value,
      auth: this.editForm.get(['auth'])!.value,
      perfilId: this.editForm.get(['perfilId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPushSubscription>>): void {
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

  trackById(index: number, item: IPerfilUsuario): any {
    return item.id;
  }
}
