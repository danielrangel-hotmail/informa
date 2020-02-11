import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPostReacao, PostReacao } from 'app/shared/model/post-reacao.model';
import { PostReacaoService } from './post-reacao.service';
import { PostReacaoComponent } from './post-reacao.component';
import { PostReacaoDetailComponent } from './post-reacao-detail.component';
import { PostReacaoUpdateComponent } from './post-reacao-update.component';

@Injectable({ providedIn: 'root' })
export class PostReacaoResolve implements Resolve<IPostReacao> {
  constructor(private service: PostReacaoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPostReacao> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((postReacao: HttpResponse<PostReacao>) => {
          if (postReacao.body) {
            return of(postReacao.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PostReacao());
  }
}

export const postReacaoRoute: Routes = [
  {
    path: '',
    component: PostReacaoComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'informaApp.postReacao.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PostReacaoDetailComponent,
    resolve: {
      postReacao: PostReacaoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'informaApp.postReacao.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PostReacaoUpdateComponent,
    resolve: {
      postReacao: PostReacaoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'informaApp.postReacao.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PostReacaoUpdateComponent,
    resolve: {
      postReacao: PostReacaoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'informaApp.postReacao.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
