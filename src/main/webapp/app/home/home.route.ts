import { Route } from '@angular/router';

import { HomeComponent } from './home.component';
import {PostComponent} from 'app/entities/shared-post/post.component';

export const HOME_ROUTE: Route = {
  path: '',
  component: HomeComponent,
  data: {
    authorities: [],
    pageTitle: 'home.title'
  },
  children: [
    { path: '', component: PostComponent }
  ]
};
