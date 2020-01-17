import {Component, Input, OnInit} from '@angular/core';
import {HttpResponse} from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import {FormBuilder, FormGroup} from '@angular/forms';
import {ActivatedRoute} from '@angular/router';
import {Observable} from 'rxjs';
import {debounceTime, distinctUntilChanged, filter} from 'rxjs/operators';
import * as moment from 'moment';
import {DATE_TIME_FORMAT} from 'app/shared/constants/input.constants';

import {ILinkExterno, LinkExterno} from 'app/shared/model/link-externo.model';
import {LinkExternoService} from '../link-externo/link-externo.service';
import {IUser} from 'app/core/user/user.model';
import {IPost} from 'app/shared/model/post.interface';
import {IMensagem} from 'app/shared/model/mensagem.model';
import {EmbedVideoService} from 'ngx-embed-video';
import {LinkTipo} from 'app/shared/model/enumerations/link-tipo.model';

type SelectableEntity = IUser | IPost | IMensagem;

const YOUTUBE_URL_REGEX = new RegExp('(http|https)?:\\/\\/(www\\.)?(?:youtube\\.com\\/\\S*(?:(?:\\/e(?:mbed))?\\/|watch\\?(?:\\S*?&?v\\=))|youtu\\.be\\/)([a-zA-Z0-9_-]{6,11})');
const DAILYMOTION_URL_REGEX = new RegExp('(http|https)?:\\/\\/(www\\.)?(?:dailymotion\\.com(?:\\/video|\\/hub)|dai\\.ly)\\/([0-9a-z]+)(?:[\\-_0-9a-zA-Z]+#video=([a-z0-9]+))?');
const VIMEO_URL_REGEX = new RegExp('(http|https)?:\\/\\/(www\\.)?vimeo.com\\/(?:channels\\/(?:\\w+\\/)?|groups\\/([^\\/]*)\\/videos\\/|)(\\d+)(?:|\\/\\?)');

const UrlValidator = (controlName: string) => (formGroup: FormGroup) => {
  const control = formGroup.controls[controlName];
  if (control.errors && !control.errors.notMatching) {
    return;
  }
  const url = formGroup ? (formGroup.get(controlName) ? formGroup.get(controlName) : null) : null;
  if ((url) && (url.value) && (
      (!url.value.match(YOUTUBE_URL_REGEX)) &&
      (!url.value.match(DAILYMOTION_URL_REGEX)) &&
      (!url.value.match(VIMEO_URL_REGEX))
      )) {
      control.setErrors({notMatching: true});
  } else {
      control.setErrors(null);
  }
}


@Component({
  selector: 'jhi-link-externo-shared',
  templateUrl: './link-externo-shared.component.html'
})
export class LinkExternoSharedComponent implements OnInit {
  @Input() post?: IPost;
  isSaving = false;
  iframeHtml: any;
  linkExterno?: ILinkExterno;
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
    usuarioId: [],
    postId: [],
    mensagemId: []
  }, { validator: UrlValidator("link") });

  constructor(
    protected linkExternoService: LinkExternoService,
    protected activatedRoute: ActivatedRoute,
    protected embedService: EmbedVideoService,
    private fb: FormBuilder
  ) {
    this.editForm.valueChanges
      .pipe(filter(() => !this.isSaving))
      .pipe(debounceTime(500))
      .subscribe((value) => {
        if ((this.editForm.valid) && (value.link)) {
          this.iframeHtml = this.embedService.embed(value.link ,
            { attr: { width: 600, height: 300 } } );
          if ((this.linkExterno) && (value.link === this.linkExterno.link )) return;
          this.save();
        } else {
          this.iframeHtml = null;
          if (this.linkExterno !== undefined) this.delete();
        }
      });
  }

  ngOnInit(): void {
    // eslint-disable-next-line no-console
    this.linkExterno = this.linkDeImagem(this.post!);
    if (this.linkExterno) {
      this.updateForm(this.linkExterno);
    } else {
      this.updateFormToCreation();
    }
  }

  updateFormToCreation(): void {
    this.editForm.patchValue({
      postId: this.post!.id,
      tipo: LinkTipo.VIDEO
    });
  }
  linkDeImagem(post: IPost): ILinkExterno | undefined {
    return post.linksExternos!.find( link => link.tipo === "VIDEO");
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

  save(): void {
    this.isSaving = true;
    const linkExternoNovo = this.createFromForm();
    if (linkExternoNovo.id != null) {
      this.subscribeToUpdateResponse(this.linkExternoService.update(linkExternoNovo));
    } else {
      this.subscribeToCreateResponse(this.linkExternoService.create(linkExternoNovo));
    }
  }

  delete(): void {
    this.isSaving = true;
    this.subscribeToDelete(this.linkExternoService.delete(this.linkExterno!.id!));
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

  protected subscribeToCreateResponse(result: Observable<HttpResponse<ILinkExterno>>): void {
    result.subscribe(
      (httpResponse) => this.onCreateSuccess(httpResponse.body!),
      () => this.onSaveError()
    );
  }

  protected subscribeToUpdateResponse(result: Observable<HttpResponse<ILinkExterno>>): void {
    result.subscribe(
      (httpResponse) => this.onUpdateSuccess(httpResponse.body!),
      () => this.onSaveError()
    );
  }

  protected subscribeToDelete(result: Observable<HttpResponse<any>>): void {
    result.subscribe(
      (httpResponse) => this.onDeleteSuccess(),
      () => this.onSaveError()
    );
  }

  protected onCreateSuccess(link: ILinkExterno): void {
    this.linkExterno = link;
    this.post!.linksExternos!.push(link);
    this.updateForm(link);
    this.isSaving = false;
  }

  protected onUpdateSuccess(link: ILinkExterno): void {
    const index = this.post!.linksExternos!.findIndex((le) => le.id === link.id);
    this.post!.linksExternos!.splice(index, 1, link);
    this.updateForm(link);
    this.isSaving = false;
  }

  protected onDeleteSuccess(): void {
    const index = this.post!.linksExternos!.findIndex((le) => le.id === this.linkExterno!.id);
    this.post!.linksExternos!.splice(index, 1);
    this.linkExterno = undefined;
    this.updateFormToCreation();
    this.isSaving = false;
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
