import { Component, OnInit } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { SessionStorageService } from 'ngx-webstorage';

import { DEBUG_INFO_ENABLED, VERSION } from 'app/app.constants';
import { LANGUAGES } from 'app/core/language/language.constants';
import { AccountService } from 'app/core/auth/account.service';
import { LoginModalService } from 'app/core/login/login-modal.service';
import { LoginService } from 'app/core/login/login.service';
import { ProfileService } from 'app/layouts/profiles/profile.service';
import {
  DOCKED,
  MOBILE,
  PerfilGrupoViewService,
  UNDOCKABLE,
  UNDOCKED
} from 'app/layouts/navbar/perfil-grupo-view.service';
import { InsistenceEnvironmentService } from 'app/shared/insistence-environment/insistence-environment.service';
import { tap } from 'rxjs/operators';

@Component({
  selector: 'jhi-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['navbar.scss']
})
export class NavbarComponent implements OnInit {
  inProduction?: boolean;
  isNavbarCollapsed = true;
  languages = LANGUAGES;
  swaggerEnabled?: boolean;
  version: string;
  UNDOCKED = UNDOCKED;
  DOCKED = DOCKED;
  UNDOCKABLE = UNDOCKABLE;
  MOBILE = MOBILE;
  perfilGrupoOpened = false;
  DEBUG_INFO_ENABLED = DEBUG_INFO_ENABLED;

  constructor(
    private loginService: LoginService,
    private languageService: JhiLanguageService,
    private sessionStorage: SessionStorageService,
    private accountService: AccountService,
    private loginModalService: LoginModalService,
    private profileService: ProfileService,
    private router: Router,
    public perfilGrupoViewService: PerfilGrupoViewService,
    private environmentService: InsistenceEnvironmentService,
  ) {
    this.version = VERSION ? (VERSION.toLowerCase().startsWith('v') ? VERSION : 'v' + VERSION) : '';
  }

  toggleOpened(dockingState: string): void {
    if (dockingState === this.DOCKED) return;
    this.perfilGrupoOpened = !this.perfilGrupoOpened;
  }

  dock() : void {
    this.perfilGrupoViewService.dock();
    this.perfilGrupoOpened = false;
  }

  ngOnInit(): void {
    this.profileService.getProfileInfo().subscribe(profileInfo => {
      this.inProduction = profileInfo.inProduction;
      this.swaggerEnabled = profileInfo.swaggerEnabled;
    });

    this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        this.perfilGrupoOpened = false;
      }
    });
  }

  changeLanguage(languageKey: string): void {
    this.sessionStorage.store('locale', languageKey);
    this.languageService.changeLanguage(languageKey);
  }

  collapseNavbar(): void {
    this.isNavbarCollapsed = true;
  }

  isAuthenticated(): boolean {
    return this.accountService.isAuthenticated();
  }

  login(): void {
    this.loginModalService.open();
  }

  logout(): void {
    this.collapseNavbar();
    this.loginService.logout();
    this.router.navigate(['']);
  }

  logoutResetEnvironment(): void {
    this.environmentService.resetEnvironments().subscribe(() => {
      this.logout();
    });
  }

  toggleNavbar(): void {
    this.isNavbarCollapsed = !this.isNavbarCollapsed;
  }

  getImageUrl(): string {
    return this.isAuthenticated() ? this.accountService.getImageUrl() : '';
  }
}
