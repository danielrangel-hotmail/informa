import { Component, OnInit, OnDestroy, Input, ViewChild, ViewChildren, ElementRef, QueryList, AfterViewInit, HostListener } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { MensagemService } from './mensagem.service';
import { MensagemDeleteDialogComponent } from './mensagem-delete-dialog.component';
import {IPost} from 'app/shared/model/post.interface';
import {IMensagem} from 'app/shared/model/mensagem.interface';

@Component({
  selector: 'jhi-mensagem',
  templateUrl: './mensagem.component.html',
  styleUrls: [ './mensagem.component.scss' ]
})
export class MensagemComponent implements OnInit, OnDestroy, AfterViewInit {
  @ViewChild('message_container', {static: false}) scrollFrame!: ElementRef;
  @ViewChildren('message') itemElements!: QueryList<any>;
  private scrollContainer: any;
  @Input() post!: IPost;
  mensagems: IMensagem[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;
  reseting = false;

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
    this.predicate = 'criacao';
    this.ascending = false;
  }


  ngAfterViewInit(): void {
    // this.scrollContainer = this.scrollFrame.nativeElement;
    // this.itemElements.changes.subscribe(() => this.onItemElementsChanged());
  }

  private onItemElementsChanged(): void {
    this.scrollToBottom();
  }

  private scrollToBottom(): void {
    // this.scrollContainer.scroll({
    //   top: this.scrollContainer.scrollHeight,
    //   left: 0,
    //   behavior: 'smooth'
    // });
  }

  loadAll(): void {
    this.mensagemService
      .query( this.post.id!, {
          page: this.page,
          size: this.itemsPerPage,
          sort: this.sort()
      })
      .subscribe((res: HttpResponse<IMensagem[]>) => this.paginateMensagems(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.reseting = true;
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
    if (this.reseting) {
      this.mensagems = [];
      this.reseting = false;
    }
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = data.length - 1; i >= 0; i--) {
        data[i].conversaContinuada = false;
        if (this.mensagems.length > 0) {
          const ultimaMensagem = this.mensagems[this.mensagems.length - 1];
          if (ultimaMensagem.autorId === data[i].autorId) {
            data[i].conversaContinuada = true;
          }
        }
        this.mensagems.push(data[i]);
      }
    }
  }
}
