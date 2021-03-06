import {
  AfterViewInit,
  ChangeDetectorRef,
  Component,
  ElementRef,
  Input,
  OnDestroy,
  OnInit,
  QueryList,
  ViewChild,
  ViewChildren
} from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Observable, Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
// import { ITEMS_PER_PAGE } from '../../shared/constants/pagination.constants';
import { MensagemService } from './mensagem.service';
import { MensagemDeleteDialogComponent } from './mensagem-delete-dialog.component';
import { IPost } from '../../shared/model/post.interface';
import { IMensagem } from '../../shared/model/mensagem.interface';
import { DOCKED, PerfilGrupoViewService } from 'app/layouts/navbar/perfil-grupo-view.service';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/user/account.model';

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
  mensagemEditada: IMensagem | null = null;
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;
  reseting = false;
  DOCKED = DOCKED;
  account$?: Observable<Account | null>;

  constructor(
    private ref: ChangeDetectorRef,
    protected accountService: AccountService,
    protected mensagemService: MensagemService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks,
    public perfilGrupoViewService: PerfilGrupoViewService
  ) {
    this.account$ = this.accountService.identity();
    this.mensagems = [];
    this.itemsPerPage = 100;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'criacao';
    this.ascending = false;
  }


  ngAfterViewInit(): void {
    this.scrollContainer = this.scrollFrame.nativeElement;
    this.itemElements.changes.subscribe(() => this.onItemElementsChanged());
  }

  private onItemElementsChanged(): void {
    this.scrollToBottom();
  }

  private scrollToBottom(): void {
    window.scroll({
      top: this.scrollContainer.scrollHeight,
      left: 0,
      behavior: 'auto'
    });
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

  editClicked(mensagem: IMensagem): void {
    this.mensagemEditada = mensagem;
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
    this.ref.detectChanges();
  }

  setaParaCimaClicked(account: Account): void {
    // eslint-disable-next-line no-console
    console.log("seta para cima");
    if (this.mensagems.length === 0) return;
    const ultimaMensagem = this.mensagems[this.mensagems.length - 1];
    if (account.id.toString() === ultimaMensagem.autorId!.toString()) {
      this.mensagemEditada = ultimaMensagem;
      this.ref.detectChanges();
    }
  }
}
