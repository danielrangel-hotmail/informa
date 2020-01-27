import { Route } from '@angular/router';

import { HomeComponent } from './home.component';
import {PostComponent} from 'app/entities/shared-post/post.component';
import { MEUS_GRUPOS } from 'app/entities/shared-post/post.constants';

export const HOME_ROUTE: Route = {
  path: '',
  component: HomeComponent,
  data: {
    authorities: [],
    pageTitle: 'home.title'
  },
  children: [
    { path: '', component: PostComponent, data: {filtro: MEUS_GRUPOS} }
  ]
};
