import { Component, OnInit, Input } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { Mensagem } from 'app/shared/model/mensagem.model';
import { MensagemService } from './mensagem.service';
import { IUser } from 'app/core/user/user.model';
import { IPost } from 'app/shared/model/post.interface';
import {IMensagem} from 'app/shared/model/mensagem.interface';
import { JhiEventManager } from 'ng-jhipster';

type SelectableEntity = IUser | IPost | IMensagem;

@Component({
  selector: 'jhi-mensagem-update',
  templateUrl: './mensagem-update.component.html'
})
export class MensagemUpdateComponent implements OnInit {
  isSaving = false;
  @Input() mensagem?: IMensagem;
  @Input() post?: IPost;

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
    protected eventManager: JhiEventManager,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.resetForm();
  }

  resetForm(): void {
    this.editForm.patchValue({
      id: null,
      versao: null,
      criacao: null,
      ultimaEdicao: null,
      conteudo: null,
      temConversa: null,
      autorId: null,
      conversaId: null,
      postId: this.post!.id
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
    if (mensagem.id != null) {
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
    this.eventManager.broadcast('mensagemListModification');
    this.post!.numeroDeMensagens = this.post!.numeroDeMensagens! + 1;
    this.resetForm();
    this.isSaving = false;
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
