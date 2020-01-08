import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IMensagem } from 'app/shared/model/mensagem.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { MensagemService } from './mensagem.service';
import { MensagemDeleteDialogComponent } from './mensagem-delete-dialog.component';

@Component({
  selector: 'jhi-mensagem',
  templateUrl: './mensagem.component.html'
})
export class MensagemComponent implements OnInit, OnDestroy {
  mensagems: IMensagem[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected mensagemService: MensagemService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.mensagems = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.mensagemService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IMensagem[]>) => this.paginateMensagems(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.mensagems = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInMensagems();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IMensagem): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInMensagems(): void {
    this.eventSubscriber = this.eventManager.subscribe('mensagemListModification', () => this.reset());
  }

  delete(mensagem: IMensagem): void {
    const modalRef = this.modalService.open(MensagemDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.mensagem = mensagem;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateMensagems(data: IMensagem[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.mensagems.push(data[i]);
      }
    }
  }
}
