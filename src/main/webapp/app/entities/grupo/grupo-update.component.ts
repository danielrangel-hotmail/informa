import { AfterViewInit, Component, ElementRef, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { Grupo } from 'app/shared/model/grupo.model';
import { GrupoService } from './grupo.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { ITopico } from 'app/shared/model/topico.model';
import { TopicoService } from 'app/entities/topico/topico.service';
import { PerfilGrupoViewService } from 'app/layouts/navbar/perfil-grupo-view.service';
import { IGrupo } from 'app/shared/model/grupo.interface';

@Component({
  selector: 'jhi-grupo-update',
  templateUrl: './grupo-update.component.html',
  styleUrls: [
    './grupo-update.component.scss'
  ]
})
export class GrupoUpdateComponent implements OnInit, AfterViewInit{
  isSaving = false;

  topicos: ITopico[] = [];

  editForm = this.fb.group({
    id: [],
    versao: [],
    criacao: [],
    ultimaEdicao: [],
    nome: [],
    descricao: [],
    formal: [],
    opcional: [],
    logo: [],
    logoContentType: [],
    cabecalhoSuperiorCor: [],
    cabecalhoInferiorCor: [],
    logoFundoCor: [],
    topicos: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected grupoService: GrupoService,
    protected topicoService: TopicoService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    protected perfilGrupoViewService: PerfilGrupoViewService) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ grupo }) => {
      this.updateForm(grupo);

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

  updateForm(grupo: IGrupo): void {
    this.editForm.patchValue({
      id: grupo.id,
      versao: grupo.versao,
      criacao: grupo.criacao != null ? grupo.criacao.format(DATE_TIME_FORMAT) : null,
      ultimaEdicao: grupo.ultimaEdicao != null ? grupo.ultimaEdicao.format(DATE_TIME_FORMAT) : null,
      nome: grupo.nome,
      descricao: grupo.descricao,
      formal: grupo.formal,
      opcional: grupo.opcional,
      logo: grupo.logo,
      logoContentType: grupo.logoContentType,
      cabecalhoSuperiorCor: grupo.cabecalhoSuperiorCor,
      cabecalhoInferiorCor: grupo.cabecalhoInferiorCor,
      logoFundoCor: grupo.logoFundoCor,
      topicos: grupo.topicos
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
    const grupo = this.createFromForm();
    if (grupo.id !== undefined) {
      this.subscribeToSaveResponse(this.grupoService.update(grupo));
    } else {
      this.subscribeToSaveResponse(this.grupoService.create(grupo));
    }
  }

  private createFromForm(): IGrupo {
    return {
      ...new Grupo(),
      id: this.editForm.get(['id'])!.value,
      versao: this.editForm.get(['versao'])!.value,
      criacao: this.editForm.get(['criacao'])!.value != null ? moment(this.editForm.get(['criacao'])!.value, DATE_TIME_FORMAT) : undefined,
      ultimaEdicao:
        this.editForm.get(['ultimaEdicao'])!.value != null
          ? moment(this.editForm.get(['ultimaEdicao'])!.value, DATE_TIME_FORMAT)
          : undefined,
      nome: this.editForm.get(['nome'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
      formal: this.editForm.get(['formal'])!.value,
      opcional: this.editForm.get(['opcional'])!.value,
      logoContentType: this.editForm.get(['logoContentType'])!.value,
      logo: this.editForm.get(['logo'])!.value,
      cabecalhoSuperiorCor: this.editForm.get(['cabecalhoSuperiorCor'])!.value,
      cabecalhoInferiorCor: this.editForm.get(['cabecalhoInferiorCor'])!.value,
      logoFundoCor: this.editForm.get(['logoFundoCor'])!.value,
      topicos: this.editForm.get(['topicos'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGrupo>>): void {
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

  ngAfterViewInit(): void {
    this.perfilGrupoViewService.navega(this.editForm.get(['id'])!.value ? "editando grupo" : "criando grupo");
  }

  trackById(index: number, item: ITopico): any {
    return item.id;
  }

  getSelected(selectedVals: ITopico[], option: ITopico): ITopico {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
