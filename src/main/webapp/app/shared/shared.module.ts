import { NgModule } from '@angular/core';
import { InformaSharedLibsModule } from './shared-libs.module';
import { FindLanguageFromKeyPipe } from './language/find-language-from-key.pipe';
import { AlertComponent } from './alert/alert.component';
import { AlertErrorComponent } from './alert/alert-error.component';
import { LoginModalComponent } from './login/login.component';
import { HasAnyAuthorityDirective } from './auth/has-any-authority.directive';
import { QuillEditorCustomComponent } from './quill/quill-editor-custom.component';
import { QuillModule } from 'ngx-quill';

@NgModule({
  imports: [InformaSharedLibsModule, QuillModule.forRoot() ],
  declarations: [FindLanguageFromKeyPipe, AlertComponent, AlertErrorComponent, LoginModalComponent, HasAnyAuthorityDirective, QuillEditorCustomComponent],
  entryComponents: [LoginModalComponent],
  exports: [
    InformaSharedLibsModule,
    FindLanguageFromKeyPipe,
    AlertComponent,
    AlertErrorComponent,
    LoginModalComponent,
    HasAnyAuthorityDirective,
    QuillEditorCustomComponent
  ]
})
export class InformaSharedModule {}
