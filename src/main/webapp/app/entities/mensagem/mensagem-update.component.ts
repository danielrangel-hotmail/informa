import { Component, ElementRef, EventEmitter, Inject, Input, OnInit, Output, ViewChild } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder } from '@angular/forms';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from '../../shared/constants/input.constants';

import { Mensagem } from '../../shared/model/mensagem.model';
import { MensagemService } from './mensagem.service';
import { IUser } from 'app/core/user/user.model';
import { IPost } from '../../shared/model/post.interface';
import { IMensagem } from '../../shared/model/mensagem.interface';
import { JhiEventManager } from 'ng-jhipster';
import { QuillEditorCustomComponent } from 'app/shared/quill/quill-editor-custom.component';
import { DOCUMENT } from '@angular/common';
// import { MatomoTracker } from 'ngx-matomo';

type SelectableEntity = IUser | IPost | IMensagem;

@Component({
  selector: 'jhi-mensagem-update',
  templateUrl: './mensagem-update.component.html'
})
export class MensagemUpdateComponent implements OnInit {
  isSaving = false;
  _mensagem?: IMensagem;
  @Input() post?: IPost;
  @Output() setaParaCima = new EventEmitter();

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
    private fb: FormBuilder,
    @Inject(DOCUMENT) document: any
    // protected matomoTracker: MatomoTracker
  ) {}

  @Input() set mensagem(umaMensagem: IMensagem) {
    this._mensagem = umaMensagem;
    if (umaMensagem === null) return;
    this.updateForm(umaMensagem);

    // Pegar o último editor e mandar o focus para ele.
    const editors = document.getElementsByClassName("ql-editor");
    if (editors.length === 0) return;
    const editor = editors.item(editors.length - 1) as HTMLElement;
    // eslint-disable-next-line no-console
    console.log(editor);
    editor.focus();
  }

  get mensagem(): IMensagem {
    return this._mensagem!;
  }

  ngOnInit(): void {
    this.resetForm();
  }

  resetForm(): void {
    this._mensagem = undefined;
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
    if (this.isSaving) return;
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
    this.analyticsEvent();
    this.post!.numeroDeMensagens = this.post!.numeroDeMensagens! + 1;
    this.resetForm();
    this.isSaving = false;
    this.eventManager.broadcast('mensagemListModification');
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }

  analyticsEvent(): void {
    // this.matomoTracker.trackEvent('Mensagem', 'envioMensagem', this.post!.grupoId!.toString(), this.post!.id);

  }

  setaParaCimaCliked(): void {
    if (!this._mensagem) {
      this.setaParaCima.emit("seta para cima");
    }
  }
}
