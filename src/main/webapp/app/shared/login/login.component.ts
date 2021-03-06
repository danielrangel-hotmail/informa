import { AfterViewInit, Component, ElementRef, Renderer, ViewChild } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Router } from '@angular/router';

import { LoginService } from '../../core/login/login.service';
import { DEBUG_INFO_ENABLED } from '../../app.constants';
import { IInsistenceEnvironment } from '../insistence-environment/insistence.environment.interface';
import { InsistenceEnvironmentService } from '../insistence-environment/insistence-environment.service';
import { switchMap } from 'rxjs/operators';

@Component({
  selector: 'jhi-login-modal',
  templateUrl: './login.component.html'
})
export class LoginModalComponent implements AfterViewInit {
  @ViewChild('username', { static: false })
  username?: ElementRef;
  DEBUG_INFO_ENABLED = DEBUG_INFO_ENABLED;
  authenticationError = false;
  environmentToRun?: IInsistenceEnvironment;

  loginForm = this.fb.group({
    username: [''],
    password: [''],
    rememberMe: [false]
  });

  constructor(
    private loginService: LoginService,
    private renderer: Renderer,
    private router: Router,
    public activeModal: NgbActiveModal,
    private fb: FormBuilder,
    private environmentService: InsistenceEnvironmentService
  ) {}

  ngAfterViewInit(): void {
    if (this.username) {
      this.renderer.invokeElementMethod(this.username.nativeElement, 'focus', []);
    }
  }

  cancel(): void {
    this.authenticationError = false;
    this.loginForm.patchValue({
      username: '',
      password: ''
    });
    this.activeModal.dismiss('cancel');
  }

  login(): void {
    this.environmentService.setEnvironment(this.environmentToRun).pipe(
      switchMap(
        () => this.loginService
          .login({
            username: this.loginForm.get('username')!.value,
            password: this.loginForm.get('password')!.value,
            rememberMe: this.loginForm.get('rememberMe')!.value
          })
      )
    ).subscribe(
        () => {
          this.authenticationError = false;
          this.activeModal.close();
          if (
            this.router.url === '/account/register' ||
            this.router.url.startsWith('/account/activate') ||
            this.router.url.startsWith('/account/reset/')
          ) {
            this.router.navigate(['']);
          }
        },
        () => (
          this.authenticationError = true
        )
      );
  }

  register(): void {
    this.activeModal.dismiss('to state register');
    this.router.navigate(['/account/register']);
  }

  requestResetPassword(): void {
    this.activeModal.dismiss('to state requestReset');
    this.router.navigate(['/account/reset', 'request']);
  }

  selectedEnvironment(env: IInsistenceEnvironment): void {
    this.environmentToRun = env;
  }
}
