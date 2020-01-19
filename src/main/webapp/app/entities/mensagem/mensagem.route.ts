import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Mensagem } from 'app/shared/model/mensagem.model';
import { MensagemService } from './mensagem.service';
import { MensagemComponent } from './mensagem.component';
import { MensagemDetailComponent } from './mensagem-detail.component';
import { MensagemUpdateComponent } from './mensagem-update.component';
import {IMensagem} from 'app/shared/model/mensagem.interface';

@Injectable({ providedIn: 'root' })
export class MensagemResolve implements Resolve<IMensagem> {
  constructor(private service: MensagemService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMensagem> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((mensagem: HttpResponse<Mensagem>) => {
          if (mensagem.body) {
            return of(mensagem.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Mensagem());
  }
}

export const mensagemRoute: Routes = [
  {
    path: '',
    component: MensagemComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'informaApp.mensagem.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: MensagemDetailComponent,
    resolve: {
      mensagem: MensagemResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'informaApp.mensagem.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: MensagemUpdateComponent,
    resolve: {
      mensagem: MensagemResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'informaApp.mensagem.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: MensagemUpdateComponent,
    resolve: {
      mensagem: MensagemResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'informaApp.mensagem.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
