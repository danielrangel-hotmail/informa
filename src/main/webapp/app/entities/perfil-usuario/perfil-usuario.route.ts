import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPerfilUsuario, PerfilUsuario } from '../../shared/model/perfil-usuario.model';
import { PerfilUsuarioService } from './perfil-usuario.service';
import { PerfilUsuarioComponent } from './perfil-usuario.component';
import { PerfilUsuarioDetailComponent } from './perfil-usuario-detail.component';
import { PerfilUsuarioUpdateComponent } from './perfil-usuario-update.component';

@Injectable({ providedIn: 'root' })
export class PerfilUsuarioResolve implements Resolve<IPerfilUsuario> {
  constructor(private service: PerfilUsuarioService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPerfilUsuario> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((perfilUsuario: HttpResponse<PerfilUsuario>) => {
          if (perfilUsuario.body) {
            return of(perfilUsuario.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PerfilUsuario());
  }
}

export const perfilUsuarioRoute: Routes = [
  {
    path: '',
    component: PerfilUsuarioComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'informaApp.perfilUsuario.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PerfilUsuarioDetailComponent,
    resolve: {
      perfilUsuario: PerfilUsuarioResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'informaApp.perfilUsuario.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PerfilUsuarioUpdateComponent,
    resolve: {
      perfilUsuario: PerfilUsuarioResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'informaApp.perfilUsuario.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PerfilUsuarioUpdateComponent,
    resolve: {
      perfilUsuario: PerfilUsuarioResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'informaApp.perfilUsuario.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
