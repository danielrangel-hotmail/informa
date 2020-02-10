import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITopico, Topico } from '../../shared/model/topico.model';
import { TopicoService } from './topico.service';
import { TopicoComponent } from './topico.component';
import { TopicoDetailComponent } from './topico-detail.component';
import { TopicoUpdateComponent } from './topico-update.component';

@Injectable({ providedIn: 'root' })
export class TopicoResolve implements Resolve<ITopico> {
  constructor(private service: TopicoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITopico> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((topico: HttpResponse<Topico>) => {
          if (topico.body) {
            return of(topico.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Topico());
  }
}

export const topicoRoute: Routes = [
  {
    path: '',
    component: TopicoComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'informaApp.topico.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TopicoDetailComponent,
    resolve: {
      topico: TopicoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'informaApp.topico.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TopicoUpdateComponent,
    resolve: {
      topico: TopicoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'informaApp.topico.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TopicoUpdateComponent,
    resolve: {
      topico: TopicoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'informaApp.topico.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
