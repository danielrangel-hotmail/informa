import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { Router, ActivatedRouteSnapshot, NavigationEnd, NavigationError } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';

import { AccountService } from 'app/core/auth/account.service';
import { MatomoTracker } from 'ngx-matomo';
import { DOCKED, PerfilGrupoViewService } from 'app/layouts/navbar/perfil-grupo-view.service';

@Component({
  selector: 'jhi-main',
  templateUrl: './main.component.html'
})
export class MainComponent implements OnInit {
  lastUrl = "Nenhuma";
  DOCKED = DOCKED;

  constructor(
    private accountService: AccountService,
    private translateService: TranslateService,
    private titleService: Title,
    private router: Router,
    private matomoTracker: MatomoTracker,
    protected perfilGrupoViewService: PerfilGrupoViewService
  ) { }

  ngOnInit(): void {
    // try to log in automatically
    this.accountService.identity().subscribe();
    this.accountService.getAuthenticationState().subscribe((account) => {
      if (account == null) {
        // eslint-disable-next-line no-console
        console.log("matomo id resetado")
        // this.matomoTracker.resetUserId();
      } else {
        // eslint-disable-next-line no-console
        console.log(`matomo id setado ${account.login}`)
        // this.matomoTracker.setUserId(account.login);
        // this.matomoTracker.setCustomUrl(this.lastUrl);
        // this.matomoTracker.trackPageView();
      }
    });

    this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        this.lastUrl = event.url;
        this.updateTitle();
        if (this.accountService.isAuthenticated()) {
          this.matomoTracker.setCustomUrl(event.url);
        } else {
          this.matomoTracker.setCustomUrl(event.url+'/non-authenticated');
        }
        // eslint-disable-next-line no-console
        console.log(`matomo trackPageView ${event.url}`);
        this.matomoTracker.trackPageView();
      }
      if (event instanceof NavigationError && event.error.status === 404) {
        this.router.navigate(['/404']);
      }
    });

    this.translateService.onLangChange.subscribe(() => this.updateTitle());
  }

  private getPageTitle(routeSnapshot: ActivatedRouteSnapshot): string {
    let title: string = routeSnapshot.data && routeSnapshot.data['pageTitle'] ? routeSnapshot.data['pageTitle'] : '';
    if (routeSnapshot.firstChild) {
      title = this.getPageTitle(routeSnapshot.firstChild) || title;
    }
    return title;
  }

  private updateTitle(): void {
    let pageTitle = this.getPageTitle(this.router.routerState.snapshot.root);
    if (!pageTitle) {
      pageTitle = 'global.title';
    }
    this.translateService.get(pageTitle).subscribe(title => {
      this.matomoTracker.setDocumentTitle(title);
      this.titleService.setTitle(title)
    });
  }
}
