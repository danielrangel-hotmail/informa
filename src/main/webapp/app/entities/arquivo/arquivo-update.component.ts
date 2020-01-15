import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IArquivo, Arquivo } from 'app/shared/model/arquivo.model';
import { ArquivoService } from './arquivo.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IPost } from 'app/shared/model/post.model';
import { PostService } from 'app/entities/shared-post/post.service';
import { IMensagem } from 'app/shared/model/mensagem.model';
import { MensagemService } from 'app/entities/mensagem/mensagem.service';

type SelectableEntity = IUser | IPost | IMensagem;

@Component({
  selector: 'jhi-arquivo-update',
  templateUrl: './arquivo-update.component.html'
})
export class ArquivoUpdateComponent implements OnInit {
  isSaving = false;

  users: IUser[] = [];

  posts: IPost[] = [];

  mensagems: IMensagem[] = [];

  editForm = this.fb.group({
    id: [],
    versao: [],
    criacao: [],
    ultimaEdicao: [],
    nome: [],
    colecao: [],
    tipo: [],
    link: [],
    uploadConfirmado: [],
    usuarioId: [],
    postId: [],
    mensagemId: []
  });

  constructor(
    protected arquivoService: ArquivoService,
    protected userService: UserService,
    protected postService: PostService,
    protected mensagemService: MensagemService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ arquivo }) => {
      this.updateForm(arquivo);

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

  updateForm(arquivo: IArquivo): void {
    this.editForm.patchValue({
      id: arquivo.id,
      versao: arquivo.versao,
      criacao: arquivo.criacao != null ? arquivo.criacao.format(DATE_TIME_FORMAT) : null,
      ultimaEdicao: arquivo.ultimaEdicao != null ? arquivo.ultimaEdicao.format(DATE_TIME_FORMAT) : null,
      nome: arquivo.nome,
      colecao: arquivo.colecao,
      tipo: arquivo.tipo,
      link: arquivo.link,
      uploadConfirmado: arquivo.uploadConfirmado,
      usuarioId: arquivo.usuarioId,
      postId: arquivo.postId,
      mensagemId: arquivo.mensagemId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const arquivo = this.createFromForm();
    if (arquivo.id !== undefined) {
      this.subscribeToSaveResponse(this.arquivoService.update(arquivo));
    } else {
      this.subscribeToSaveResponse(this.arquivoService.create(arquivo));
    }
  }

  private createFromForm(): IArquivo {
    return {
      ...new Arquivo(),
      id: this.editForm.get(['id'])!.value,
      versao: this.editForm.get(['versao'])!.value,
      criacao: this.editForm.get(['criacao'])!.value != null ? moment(this.editForm.get(['criacao'])!.value, DATE_TIME_FORMAT) : undefined,
      ultimaEdicao:
        this.editForm.get(['ultimaEdicao'])!.value != null
          ? moment(this.editForm.get(['ultimaEdicao'])!.value, DATE_TIME_FORMAT)
          : undefined,
      nome: this.editForm.get(['nome'])!.value,
      colecao: this.editForm.get(['colecao'])!.value,
      tipo: this.editForm.get(['tipo'])!.value,
      link: this.editForm.get(['link'])!.value,
      uploadConfirmado: this.editForm.get(['uploadConfirmado'])!.value,
      usuarioId: this.editForm.get(['usuarioId'])!.value,
      postId: this.editForm.get(['postId'])!.value,
      mensagemId: this.editForm.get(['mensagemId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IArquivo>>): void {
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
