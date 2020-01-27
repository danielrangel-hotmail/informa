import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Post } from 'app/shared/model/post.model';
import { IPost } from 'app/shared/model/post.interface';
import { PostService } from '../shared-post/post.service';
import { PostDetailComponent } from './post-detail.component';
import { PostUpdateComponent } from './post-update.component';
import { MyPostComponent } from 'app/entities/post/my-post.component';
import {PostImageComponent} from 'app/entities/post/post-image/post-image.component';
import {PostVideoLinkComponent} from 'app/entities/post/post-video-link/post-video-link.component';
import { PostComponent } from 'app/entities/shared-post/post.component';
import { GRUPO, INFORMAIS, TODOS, TRABALHO } from 'app/entities/shared-post/post.constants';
import { GrupoService } from 'app/entities/grupo/grupo.service';
import { GrupoResolve } from 'app/entities/grupo/grupo.route';

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
    component: MyPostComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'informaApp.post.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'todos',
    component: PostComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'informaApp.post.home.title',
      filtro: TODOS
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'trabalho',
    component: PostComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'informaApp.post.home.title',
      filtro: TRABALHO
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'informais',
    component: PostComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'informaApp.post.home.title',
      filtro: INFORMAIS
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'grupo/:id',
    component: PostComponent,
    resolve: {
      grupo: GrupoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'informaApp.post.home.title',
      filtro: GRUPO
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
