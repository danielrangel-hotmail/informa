import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { ITopico, Topico } from '../../shared/model/topico.model';
import { TopicoService } from './topico.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

type SelectableEntity = IUser | ITopico;

@Component({
  selector: 'jhi-topico-update',
  templateUrl: './topico-update.component.html'
})
export class TopicoUpdateComponent implements OnInit {
  isSaving = false;

  users: IUser[] = [];

  topicos: ITopico[] = [];

  editForm = this.fb.group({
    id: [],
    versao: [],
    nome: [],
    autorId: [null, Validators.required],
    substitutoId: []
  });

  constructor(
    protected topicoService: TopicoService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ topico }) => {
      this.updateForm(topico);

      this.userService
        .query()
        .pipe(
          map((res: HttpResponse<IUser[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IUser[]) => (this.users = resBody));

      this.topicoService
        .query()
        .pipe(
          map((res: HttpResponse<ITopico[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: ITopico[]) => (this.topicos = resBody));
    });
  }

  updateForm(topico: ITopico): void {
    this.editForm.patchValue({
      id: topico.id,
      versao: topico.versao,
      nome: topico.nome,
      autorId: topico.autorId,
      substitutoId: topico.substitutoId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const topico = this.createFromForm();
    if (topico.id !== undefined) {
      this.subscribeToSaveResponse(this.topicoService.update(topico));
    } else {
      this.subscribeToSaveResponse(this.topicoService.create(topico));
    }
  }

  private createFromForm(): ITopico {
    return {
      ...new Topico(),
      id: this.editForm.get(['id'])!.value,
      versao: this.editForm.get(['versao'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      autorId: this.editForm.get(['autorId'])!.value,
      substitutoId: this.editForm.get(['substitutoId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITopico>>): void {
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
