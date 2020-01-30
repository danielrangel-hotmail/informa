import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Grupo } from 'app/shared/model/grupo.model';
import { GrupoService } from './grupo.service';
import { GrupoComponent } from './grupo.component';
import { GrupoDetailComponent } from './grupo-detail.component';
import { GrupoUpdateComponent } from './grupo-update.component';
import { IGrupo } from 'app/shared/model/grupo.interface';


export const resolveGrupo$ = (route: ActivatedRouteSnapshot, service: GrupoService): Observable<IGrupo | never>  => {
  const id = route.params['id'];
  if (id) {
    return service.find(id).pipe(
      flatMap((grupo: HttpResponse<Grupo>) => {
        if (grupo.body) {
          return of(grupo.body);
        } else {
          return EMPTY;
        }
      })
    );
  }
  return of(new Grupo());
}

@Injectable({ providedIn: 'root' })
export class GrupoResolve implements Resolve<IGrupo> {
  constructor(private service: GrupoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IGrupo> | Observable<never> {
    const grupo$ = resolveGrupo$(route, this.service);
    if (grupo$ === EMPTY) { this.router.navigate(['404']); }
    return grupo$;
  }
}

export const grupoRoute: Routes = [
  {
    path: '',
    component: GrupoComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'informaApp.grupo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: GrupoDetailComponent,
    resolve: {
      grupo: GrupoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'informaApp.grupo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: GrupoUpdateComponent,
    resolve: {
      grupo: GrupoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'informaApp.grupo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: GrupoUpdateComponent,
    resolve: {
      grupo: GrupoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'informaApp.grupo.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
