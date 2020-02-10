import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ILinkExterno } from '../../shared/model/link-externo.model';

import { ITEMS_PER_PAGE } from '../../shared/constants/pagination.constants';
import { LinkExternoService } from './link-externo.service';
import { LinkExternoDeleteDialogComponent } from './link-externo-delete-dialog.component';

@Component({
  selector: 'jhi-link-externo',
  templateUrl: './link-externo.component.html'
})
export class LinkExternoComponent implements OnInit, OnDestroy {
  linkExternos: ILinkExterno[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected linkExternoService: LinkExternoService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.linkExternos = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.linkExternoService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ILinkExterno[]>) => this.paginateLinkExternos(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.linkExternos = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInLinkExternos();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ILinkExterno): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInLinkExternos(): void {
    this.eventSubscriber = this.eventManager.subscribe('linkExternoListModification', () => this.reset());
  }

  delete(linkExterno: ILinkExterno): void {
    const modalRef = this.modalService.open(LinkExternoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.linkExterno = linkExterno;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateLinkExternos(data: ILinkExterno[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.linkExternos.push(data[i]);
      }
    }
  }
}
