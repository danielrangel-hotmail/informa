import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { PerfilGrupo } from '../../shared/model/perfil-grupo.model';
import { PerfilGrupoService } from './perfil-grupo.service';
import { PerfilGrupoComponent } from './perfil-grupo.component';
import { PerfilGrupoDetailComponent } from './perfil-grupo-detail.component';
import { PerfilGrupoUpdateComponent } from './perfil-grupo-update.component';
import { IPerfilGrupo } from '../../shared/model/perfil-grupo.interface';

@Injectable({ providedIn: 'root' })
export class PerfilGrupoResolve implements Resolve<IPerfilGrupo> {
  constructor(private service: PerfilGrupoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPerfilGrupo> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((perfilGrupo: HttpResponse<PerfilGrupo>) => {
          if (perfilGrupo.body) {
            return of(perfilGrupo.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PerfilGrupo());
  }
}

export const perfilGrupoRoute: Routes = [
  {
    path: '',
    component: PerfilGrupoComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'informaApp.perfilGrupo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PerfilGrupoDetailComponent,
    resolve: {
      perfilGrupo: PerfilGrupoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'informaApp.perfilGrupo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PerfilGrupoUpdateComponent,
    resolve: {
      perfilGrupo: PerfilGrupoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'informaApp.perfilGrupo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PerfilGrupoUpdateComponent,
    resolve: {
      perfilGrupo: PerfilGrupoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'informaApp.perfilGrupo.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
