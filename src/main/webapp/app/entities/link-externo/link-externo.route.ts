import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ILinkExterno, LinkExterno } from 'app/shared/model/link-externo.model';
import { LinkExternoService } from './link-externo.service';
import { LinkExternoComponent } from './link-externo.component';
import { LinkExternoDetailComponent } from './link-externo-detail.component';
import { LinkExternoUpdateComponent } from './link-externo-update.component';

@Injectable({ providedIn: 'root' })
export class LinkExternoResolve implements Resolve<ILinkExterno> {
  constructor(private service: LinkExternoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILinkExterno> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((linkExterno: HttpResponse<LinkExterno>) => {
          if (linkExterno.body) {
            return of(linkExterno.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new LinkExterno());
  }
}

export const linkExternoRoute: Routes = [
  {
    path: '',
    component: LinkExternoComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'informaApp.linkExterno.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: LinkExternoDetailComponent,
    resolve: {
      linkExterno: LinkExternoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'informaApp.linkExterno.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: LinkExternoUpdateComponent,
    resolve: {
      linkExterno: LinkExternoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'informaApp.linkExterno.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: LinkExternoUpdateComponent,
    resolve: {
      linkExterno: LinkExternoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'informaApp.linkExterno.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
