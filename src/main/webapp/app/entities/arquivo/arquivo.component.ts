import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IArquivo } from 'app/shared/model/arquivo.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { ArquivoService } from './arquivo.service';
import { ArquivoDeleteDialogComponent } from './arquivo-delete-dialog.component';

@Component({
  selector: 'jhi-arquivo',
  templateUrl: './arquivo.component.html'
})
export class ArquivoComponent implements OnInit, OnDestroy {
  arquivos: IArquivo[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    public arquivoService: ArquivoService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.arquivos = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.arquivoService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IArquivo[]>) => this.paginateArquivos(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.arquivos = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInArquivos();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IArquivo): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInArquivos(): void {
    this.eventSubscriber = this.eventManager.subscribe('arquivoListModification', () => this.reset());
  }

  delete(arquivo: IArquivo): void {
    const modalRef = this.modalService.open(ArquivoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.arquivo = arquivo;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateArquivos(data: IArquivo[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.arquivos.push(data[i]);
      }
    }
  }
}
