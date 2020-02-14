import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IPostReacao, PostReacao } from 'app/shared/model/post-reacao.model';
import { PostReacaoService } from './post-reacao.service';
import { IPerfilUsuario } from 'app/shared/model/perfil-usuario.model';
import { PerfilUsuarioService } from 'app/entities/perfil-usuario/perfil-usuario.service';
import { IPost } from 'app/shared/model/post.interface';
import { PostService } from 'app/entities/shared-post/post.service';

type SelectableEntity = IPerfilUsuario | IPost;

@Component({
  selector: 'jhi-post-reacao-update',
  templateUrl: './post-reacao-update.component.html'
})
export class PostReacaoUpdateComponent implements OnInit {
  isSaving = false;

  perfilusuarios: IPerfilUsuario[] = [];

  posts: IPost[] = [];

  editForm = this.fb.group({
    id: [],
    criacao: [],
    ultimaEdicao: [],
    versao: [],
    reacao: [],
    perfilId: [null, Validators.required],
    postId: [null, Validators.required]
  });

  constructor(
    protected postReacaoService: PostReacaoService,
    protected perfilUsuarioService: PerfilUsuarioService,
    protected postService: PostService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ postReacao }) => {
      this.updateForm(postReacao);

      this.perfilUsuarioService
        .query()
        .pipe(
          map((res: HttpResponse<IPerfilUsuario[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IPerfilUsuario[]) => (this.perfilusuarios = resBody));

      this.postService
        .query()
        .pipe(
          map((res: HttpResponse<IPost[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IPost[]) => (this.posts = resBody));
    });
  }

  updateForm(postReacao: IPostReacao): void {
    this.editForm.patchValue({
      id: postReacao.id,
      criacao: postReacao.criacao != null ? postReacao.criacao.format(DATE_TIME_FORMAT) : null,
      ultimaEdicao: postReacao.ultimaEdicao != null ? postReacao.ultimaEdicao.format(DATE_TIME_FORMAT) : null,
      versao: postReacao.versao,
      reacao: postReacao.reacao,
      perfilId: postReacao.perfilId,
      postId: postReacao.postId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const postReacao = this.createFromForm();
    if (postReacao.id !== undefined) {
      // this.subscribeToSaveResponse(this.postReacaoService.update(postReacao));
    } else {
      // this.subscribeToSaveResponse(this.postReacaoService.create(postReacao));
    }
  }

  private createFromForm(): IPostReacao {
    return {
      ...new PostReacao(),
      id: this.editForm.get(['id'])!.value,
      criacao: this.editForm.get(['criacao'])!.value != null ? moment(this.editForm.get(['criacao'])!.value, DATE_TIME_FORMAT) : undefined,
      ultimaEdicao:
        this.editForm.get(['ultimaEdicao'])!.value != null
          ? moment(this.editForm.get(['ultimaEdicao'])!.value, DATE_TIME_FORMAT)
          : undefined,
      versao: this.editForm.get(['versao'])!.value,
      reacao: this.editForm.get(['reacao'])!.value,
      perfilId: this.editForm.get(['perfilId'])!.value,
      postId: this.editForm.get(['postId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPostReacao>>): void {
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
