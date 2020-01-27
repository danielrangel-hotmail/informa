import { NgModule } from '@angular/core';
import { InformaSharedLibsModule } from './shared-libs.module';
import { FindLanguageFromKeyPipe } from './language/find-language-from-key.pipe';
import { AlertComponent } from './alert/alert.component';
import { AlertErrorComponent } from './alert/alert-error.component';
import { LoginModalComponent } from './login/login.component';
import { HasAnyAuthorityDirective } from './auth/has-any-authority.directive';
import { QuillEditorCustomComponent } from './quill/quill-editor-custom.component';
import { QuillModule } from 'ngx-quill';
import { HoveredComponent } from './hovered/hovered.component';
import { JhiInputUserComponent } from 'app/shared/input-user/input-user.component';
import { HoveredToggleComponent } from 'app/shared/hovered-toggle/hovered-toggle/hovered-toggle.component';

@NgModule({
  imports: [InformaSharedLibsModule, QuillModule.forRoot() ],
  declarations: [FindLanguageFromKeyPipe, AlertComponent, AlertErrorComponent, LoginModalComponent, HasAnyAuthorityDirective, QuillEditorCustomComponent, HoveredComponent, JhiInputUserComponent, HoveredToggleComponent],
  entryComponents: [LoginModalComponent],
  exports: [
    InformaSharedLibsModule,
    FindLanguageFromKeyPipe,
    AlertComponent,
    AlertErrorComponent,
    LoginModalComponent,
    HasAnyAuthorityDirective,
    QuillEditorCustomComponent,
    HoveredComponent,
    HoveredToggleComponent,
    JhiInputUserComponent
  ]
})
export class InformaSharedModule {}
