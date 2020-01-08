import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IMensagem, Mensagem } from 'app/shared/model/mensagem.model';
import { MensagemService } from './mensagem.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IPost } from 'app/shared/model/post.model';
import { PostService } from 'app/entities/shared-post/post.service';

type SelectableEntity = IUser | IPost | IMensagem;

@Component({
  selector: 'jhi-mensagem-update',
  templateUrl: './mensagem-update.component.html'
})
export class MensagemUpdateComponent implements OnInit {
  isSaving = false;

  users: IUser[] = [];

  posts: IPost[] = [];

  mensagems: IMensagem[] = [];

  editForm = this.fb.group({
    id: [],
    versao: [],
    criacao: [],
    ultimaEdicao: [],
    conteudo: [],
    temConversa: [],
    autorId: [],
    postId: [],
    conversaId: []
  });

  constructor(
    protected mensagemService: MensagemService,
    protected userService: UserService,
    protected postService: PostService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ mensagem }) => {
      this.updateForm(mensagem);

      this.userService
        .query()
        .pipe(
          map((res: HttpResponse<IUser[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IUser[]) => (this.users = resBody));

      this.postService
        .query()
        .pipe(
          map((res: HttpResponse<IPost[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IPost[]) => (this.posts = resBody));

      this.mensagemService
        .query()
        .pipe(
          map((res: HttpResponse<IMensagem[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IMensagem[]) => (this.mensagems = resBody));
    });
  }

  updateForm(mensagem: IMensagem): void {
    this.editForm.patchValue({
      id: mensagem.id,
      versao: mensagem.versao,
      criacao: mensagem.criacao != null ? mensagem.criacao.format(DATE_TIME_FORMAT) : null,
      ultimaEdicao: mensagem.ultimaEdicao != null ? mensagem.ultimaEdicao.format(DATE_TIME_FORMAT) : null,
      conteudo: mensagem.conteudo,
      temConversa: mensagem.temConversa,
      autorId: mensagem.autorId,
      postId: mensagem.postId,
      conversaId: mensagem.conversaId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const mensagem = this.createFromForm();
    if (mensagem.id !== undefined) {
      this.subscribeToSaveResponse(this.mensagemService.update(mensagem));
    } else {
      this.subscribeToSaveResponse(this.mensagemService.create(mensagem));
    }
  }

  private createFromForm(): IMensagem {
    return {
      ...new Mensagem(),
      id: this.editForm.get(['id'])!.value,
      versao: this.editForm.get(['versao'])!.value,
      criacao: this.editForm.get(['criacao'])!.value != null ? moment(this.editForm.get(['criacao'])!.value, DATE_TIME_FORMAT) : undefined,
      ultimaEdicao:
        this.editForm.get(['ultimaEdicao'])!.value != null
          ? moment(this.editForm.get(['ultimaEdicao'])!.value, DATE_TIME_FORMAT)
          : undefined,
      conteudo: this.editForm.get(['conteudo'])!.value,
      temConversa: this.editForm.get(['temConversa'])!.value,
      autorId: this.editForm.get(['autorId'])!.value,
      postId: this.editForm.get(['postId'])!.value,
      conversaId: this.editForm.get(['conversaId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMensagem>>): void {
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
