import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import {IMensagem} from 'app/shared/model/mensagem.interface';
import {MensagemPostComponent} from 'app/entities/mensagem/mensagem-post/mensagem-post.component';
import {PostService} from 'app/entities/shared-post/post.service';
import {IPost} from 'app/shared/model/post.interface';
import {Post} from 'app/shared/model/post.model';

@Injectable({ providedIn: 'root' })
export class PostResolve implements Resolve<IMensagem> {
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

export const mensagemRoute: Routes = [
  {
    path: 'post/:id',
    component: MensagemPostComponent,
    resolve: {
      post: PostResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'informaApp.mensagem.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
