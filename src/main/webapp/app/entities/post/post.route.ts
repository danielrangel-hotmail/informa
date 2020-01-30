import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY, forkJoin } from 'rxjs';
import { flatMap, map, switchMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Post } from 'app/shared/model/post.model';
import { IPost } from 'app/shared/model/post.interface';
import { PostService } from '../shared-post/post.service';
import { PostDetailComponent } from './post-detail.component';
import { PostUpdateComponent } from './post-update.component';
import {PostImageComponent} from 'app/entities/post/post-image/post-image.component';
import {PostVideoLinkComponent} from 'app/entities/post/post-video-link/post-video-link.component';
import { PostComponent } from 'app/entities/shared-post/post.component';
import { DRAFTS, GRUPO, INFORMAIS, TODOS, TRABALHO } from 'app/entities/shared-post/post.constants';
import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { GrupoService } from 'app/entities/grupo/grupo.service';
import { Grupo, IGrupo } from 'app/shared/model/grupo.model';
import { AccountService } from 'app/core/auth/account.service';


const resolveGrupo$ = (id: number, service: GrupoService): Observable<IGrupo | never>  => {
  return service.find(id).pipe(
    flatMap((grupo: HttpResponse<Grupo>) => {
      if (grupo.body) {
        return of(grupo.body);
      } else {
        return EMPTY;
      }
    }))
}

@Injectable({ providedIn: 'root' })
export class PostsGrupoResolve implements Resolve<HttpResponse<IPost[]>> {
  constructor(private service: PostService, private grupoService: GrupoService, private accountService: AccountService) {}
  resolve(route: ActivatedRouteSnapshot): Observable<any> {
    if (!this.accountService.isAuthenticated()) {
      return of({
        httpPosts: null,
        grupo: null,
      });
    }
    const id = route.params['id'];
    if (id) {
      return resolveGrupo$(id, this.grupoService).pipe(
        switchMap(grupo =>
          forkJoin(
            {
              grupo: of(grupo),
              httpPosts: this.service.loadAll(0, ITEMS_PER_PAGE, route.data.predicate, route.data.filtro, grupo)
            }
          ))
      );
    }
    return this.service.loadAll(0, ITEMS_PER_PAGE, route.data.predicate, route.data.filtro, undefined).pipe(
      map(httpPosts => (
        {
        httpPosts,
        grupo: null
      }))
    );
  }
}


@Injectable({ providedIn: 'root' })
export class PostResolve implements Resolve<IPost> {
  constructor(private service: PostService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPost> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((post: HttpResponse<Post>) => {
          if (post.body) {
            return of(post.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Post());
  }
}

export const postRoute: Routes = [
  {
    path: '',
    component: PostComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'informaApp.post.home.title',
      filtro: DRAFTS,
      predicate: 'criacao'
    },
    resolve: {
      postsGrupo: PostsGrupoResolve
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'todos',
    component: PostComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'informaApp.post.home.title',
      filtro: TODOS,
      predicate: 'publicacao',
    },
    resolve: {
      postsGrupo: PostsGrupoResolve
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'trabalho',
    component: PostComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'informaApp.post.home.title',
      filtro: TRABALHO,
      predicate: 'publicacao'
    },
    resolve: {
      postsGrupo: PostsGrupoResolve
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'informais',
    component: PostComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'informaApp.post.home.title',
      filtro: INFORMAIS,
      predicate: 'publicacao'
    },
    resolve: {
      postsGrupo: PostsGrupoResolve
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'grupo/:id',
    component: PostComponent,
    resolve: {
      postsGrupo: PostsGrupoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'informaApp.post.home.title',
      filtro: GRUPO,
      predicate: 'publicacao'
    },
    canActivate: [UserRouteAccessService]
  },

  {
    path: ':id/view',
    component: PostDetailComponent,
    resolve: {
      post: PostResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'informaApp.post.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PostUpdateComponent,
    resolve: {
      post: PostResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'informaApp.post.home.title'
    },
    canActivate: [UserRouteAccessService],
    children: [
      {path: 'image', component: PostImageComponent},
      {path: 'video-link', component: PostVideoLinkComponent},
      {path: '', redirectTo:'image', pathMatch:"full" },
    ]
  },
  {
    path: ':id/edit',
    component: PostUpdateComponent,
    resolve: {
      post: PostResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'informaApp.post.home.title'
    },
    canActivate: [UserRouteAccessService],
    children: [
      {path: 'image', component: PostImageComponent},
      {path: 'video-link', component: PostVideoLinkComponent},
      {path: '', redirectTo:'image', pathMatch:"full" },
    ]
  }
];
