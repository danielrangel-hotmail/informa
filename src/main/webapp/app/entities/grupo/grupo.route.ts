import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY, forkJoin } from 'rxjs';
import { flatMap, map } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Grupo } from '../../shared/model/grupo.model';
import { GrupoService } from './grupo.service';
import { GrupoComponent } from './grupo.component';
import { GrupoDetailComponent } from './grupo-detail.component';
import { GrupoUpdateComponent } from './grupo-update.component';
import { IGrupo } from '../../shared/model/grupo.interface';
import { AccountService } from 'app/core/auth/account.service';


export const resolveGrupo$ = (route: ActivatedRouteSnapshot, service: GrupoService, accountService: AccountService): Observable<IGrupo | never>  => {
  const id = route.params['id'];
  if (id) {
    return service.findComUsuarios(id).pipe(
      flatMap((grupo: HttpResponse<Grupo>) => {
        if (grupo.body) {
          return of(grupo.body);
        } else {
          return EMPTY;
        }
      })
    );
  }
  return accountService.identityAsSimpleUser$().pipe(
    map(simpleUser => {
      const grupo = new Grupo();
      grupo.cabecalhoSuperiorCor = '#ffa500';
      grupo.cabecalhoInferiorCor = '#ffffff';
      grupo.logoFundoCor = '#ffffff';
      grupo.moderadores = [simpleUser!];
      grupo.opcional = true;
      return grupo;
    })
  )
}

@Injectable({ providedIn: 'root' })
export class GrupoResolve implements Resolve<IGrupo> {
  constructor(private service: GrupoService, private router: Router, private accountService: AccountService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IGrupo> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.findComUsuarios(id).pipe(
        flatMap((grupo: HttpResponse<Grupo>) => {
          if (grupo.body) {
            return of(grupo.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return this.accountService.identityAsSimpleUser$().pipe(
      map(simpleUser => {
        const grupo = new Grupo();
        grupo.cabecalhoSuperiorCor = '#ffa500';
        grupo.cabecalhoInferiorCor = '#ffffff';
        grupo.logoFundoCor = '#ffffff';
        grupo.moderadores = [simpleUser!];
        grupo.opcional = true;
        return grupo;
      })
    );
  }
}

@Injectable({ providedIn: 'root' })
export class GrupoResolveToEdit implements Resolve<IGrupo> {
  constructor(private service: GrupoService, private router: Router, private accountService: AccountService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IGrupo> | Observable<never> {
    const id = route.params['id'];
    if (id) {
        return forkJoin({
          grupoHttp: this.service.findComUsuarios(id),
          simpleUser: this.accountService.identityAsSimpleUser$()
        }).pipe(
          flatMap(({grupoHttp, simpleUser}) => {
            if (!grupoHttp.body) {
              this.router.navigate(['404']);
              return EMPTY;
            }
            const grupo = grupoHttp.body;
            if (this.accountService.hasAnyAuthority(['ROLE_ADMIN'])) {
              return of(grupo);
            }
            if (grupo.moderadores!.find(mod => mod.id === simpleUser!.id)) {
              return of(grupo);
            } else {
              this.router.navigate(['accessdenied']);
              return EMPTY;
            }
          })
        );
    }
    this.router.navigate(['404']);
    return EMPTY;

    return this.accountService.identityAsSimpleUser$().pipe(
      map(simpleUser => {
        const grupo = new Grupo();
        grupo.cabecalhoSuperiorCor = '#ffa500';
        grupo.cabecalhoInferiorCor = '#ffffff';
        grupo.logoFundoCor = '#ffffff';
        grupo.moderadores = [simpleUser!];
        grupo.opcional = true;
        return grupo;
      })
    );
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
      grupo: GrupoResolveToEdit
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'informaApp.grupo.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
