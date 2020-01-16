import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ILinkExterno, LinkExterno } from 'app/shared/model/link-externo.model';
import { LinkExternoService } from './link-externo.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IPost } from 'app/shared/model/post.model';
import { IMensagem } from 'app/shared/model/mensagem.model';
import { MensagemService } from 'app/entities/mensagem/mensagem.service';
import {PostService} from 'app/entities/shared-post/post.service';

type SelectableEntity = IUser | IPost | IMensagem;

@Component({
  selector: 'jhi-link-externo-update',
  templateUrl: './link-externo-update.component.html'
})
export class LinkExternoUpdateComponent implements OnInit {
  isSaving = false;

  users: IUser[] = [];

  posts: IPost[] = [];

  mensagems: IMensagem[] = [];

  editForm = this.fb.group({
    id: [],
    versao: [],
    criacao: [],
    ultimaEdicao: [],
    tipo: [],
    link: [],
    usuarioId: [null, Validators.required],
    postId: [],
    mensagemId: []
  });

  constructor(
    protected linkExternoService: LinkExternoService,
    protected userService: UserService,
    protected postService: PostService,
    protected mensagemService: MensagemService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ linkExterno }) => {
      this.updateForm(linkExterno);

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

  updateForm(linkExterno: ILinkExterno): void {
    this.editForm.patchValue({
      id: linkExterno.id,
      versao: linkExterno.versao,
      criacao: linkExterno.criacao != null ? linkExterno.criacao.format(DATE_TIME_FORMAT) : null,
      ultimaEdicao: linkExterno.ultimaEdicao != null ? linkExterno.ultimaEdicao.format(DATE_TIME_FORMAT) : null,
      tipo: linkExterno.tipo,
      link: linkExterno.link,
      usuarioId: linkExterno.usuarioId,
      postId: linkExterno.postId,
      mensagemId: linkExterno.mensagemId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const linkExterno = this.createFromForm();
    if (linkExterno.id !== undefined) {
      this.subscribeToSaveResponse(this.linkExternoService.update(linkExterno));
    } else {
      this.subscribeToSaveResponse(this.linkExternoService.create(linkExterno));
    }
  }

  private createFromForm(): ILinkExterno {
    return {
      ...new LinkExterno(),
      id: this.editForm.get(['id'])!.value,
      versao: this.editForm.get(['versao'])!.value,
      criacao: this.editForm.get(['criacao'])!.value != null ? moment(this.editForm.get(['criacao'])!.value, DATE_TIME_FORMAT) : undefined,
      ultimaEdicao:
        this.editForm.get(['ultimaEdicao'])!.value != null
          ? moment(this.editForm.get(['ultimaEdicao'])!.value, DATE_TIME_FORMAT)
          : undefined,
      tipo: this.editForm.get(['tipo'])!.value,
      link: this.editForm.get(['link'])!.value,
      usuarioId: this.editForm.get(['usuarioId'])!.value,
      postId: this.editForm.get(['postId'])!.value,
      mensagemId: this.editForm.get(['mensagemId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILinkExterno>>): void {
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
