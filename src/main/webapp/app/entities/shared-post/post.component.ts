import { Component, OnInit, OnDestroy, Input } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription, Observable } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPost } from 'app/shared/model/post.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { PostService } from './post.service';
import { PostDeleteDialogComponent } from '../post/post-delete-dialog.component';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/user/account.model';
import { PostPublicaDialogComponent } from './post-publica-dialog.component';

@Component({
  selector: 'jhi-post',
  templateUrl: './post.component.html'
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

  constructor(
    protected postService: PostService,
    protected accountService: AccountService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.posts = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'criacao';
    this.ascending = false;
  }

  loadAll(): void {
    if (this.onlyLoggedUser) {
      this.postService
        .queryLoggedUser({
          page: this.page,
          size: this.itemsPerPage,
          sort: this.sort()
        })
        .subscribe((res: HttpResponse<IPost[]>) => this.paginatePosts(res.body, res.headers));
    } else {
      this.postService
        .query({
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
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPosts();
    this.account$ = this.accountService.identity();
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
