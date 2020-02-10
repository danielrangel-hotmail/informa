import { Route } from '@angular/router';

import { HomeComponent } from './home.component';
import {PostComponent} from '../entities/shared-post/post.component';
import { MEUS_GRUPOS } from '../entities/shared-post/post.constants';
import { PostsGrupoResolve } from '../entities/post/post.route';

export const HOME_ROUTE: Route = {
  path: '',
  component: HomeComponent,
  data: {
    pageTitle: 'home.title'
  },
  children: [
    {
      path: '', component: PostComponent,
      data: {
        filtro: MEUS_GRUPOS,
        predicate: 'publicacao'
      },
      resolve: {
        postsGrupo: PostsGrupoResolve
      }
    }
  ],
};
