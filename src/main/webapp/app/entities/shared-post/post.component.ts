import { Component, OnInit, OnDestroy, Input } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription, Observable } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import {IPost} from 'app/shared/model/post.interface';
import {Post} from 'app/shared/model/post.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { PostService } from './post.service';
import { PostDeleteDialogComponent } from './post-delete-dialog.component';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/user/account.model';
import { PostPublicaDialogComponent } from './post-publica-dialog.component';
import { ActivatedRoute, Router } from '@angular/router';
import { IGrupo } from 'app/shared/model/grupo.model';

@Component({
  selector: 'jhi-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.scss']
})
export class PostComponent implements OnInit, OnDestroy {
  @Input() onlyLoggedUser = false;
  posts: IPost[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;
  account$?: Observable<Account | null>;
  draftsQtd$?: Observable<number>;
  filtro: string;
  grupo?: IGrupo;

  constructor(
    protected postService: PostService,
    protected accountService: AccountService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks,
    protected router: Router,
    protected activatedRoute: ActivatedRoute
  ) {
    this.filtro='';
    this.loadDraftQtd();
    this.posts = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'criacao';
    this.ascending = false;
  }

  private loadDraftQtd(): void {
    this.draftsQtd$ = this.postService.countDrafts();
  }

  loadAll(): void {
    if (this.grupo != null) {
      this.postService
        .queryGrupo(this.grupo.id!,{
          page: this.page,
          size: this.itemsPerPage,
          sort: this.sort()
        })
        .subscribe((res: HttpResponse<IPost[]>) => this.paginatePosts(res.body, res.headers));
    } else {
      this.postService
        .queryFiltro(this.filtro, {
          page: this.page,
          size: this.itemsPerPage,
          sort: this.sort()
        })
        .subscribe((res: HttpResponse<IPost[]>) => this.paginatePosts(res.body, res.headers));
    }
  }

  reset(): void {
    this.page = 0;
    this.posts = [];
    this.loadDraftQtd();
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.account$ = this.accountService.identity();
    this.activatedRoute.data.subscribe(({ filtro, grupo }) => {
      this.posts = [];
      this.filtro = filtro;
      this.grupo = grupo;
      this.loadAll();
      this.registerChangeInPosts();
    });
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPost): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInPosts(): void {
    this.eventSubscriber = this.eventManager.subscribe('postListModification', () => this.reset());
  }

  publica(post: IPost): void {
    const modalRef = this.modalService.open(PostPublicaDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.post = post;
  }

  delete(post: IPost): void {
    const modalRef = this.modalService.open(PostDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.post = post;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  createPost(): void {
    this.subscribeToSaveResponse(this.postService.create(new Post()));
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPost>>): void {
    result.subscribe(
      (resultPost: HttpResponse<IPost>) => {
        const id = resultPost.body ? resultPost.body.id : null;
        // eslint-disable-next-line no-console
        console.log(`/post/${id}/edit`);
        if (id != null) this.router.navigateByUrl(`/post/${id}/edit`);
      },
      () => {}
    );
  }
  protected paginatePosts(data: IPost[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.posts.push(data[i]);
      }
    }
  }
}
