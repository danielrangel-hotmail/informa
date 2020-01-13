import { NgModule } from '@angular/core';
import { InformaSharedLibsModule } from './shared-libs.module';
import { FindLanguageFromKeyPipe } from './language/find-language-from-key.pipe';
import { AlertComponent } from './alert/alert.component';
import { AlertErrorComponent } from './alert/alert-error.component';
import { LoginModalComponent } from './login/login.component';
import { HasAnyAuthorityDirective } from './auth/has-any-authority.directive';
import { QuillEditorCustomComponent } from './quill/quill-editor-custom.component';
import { QuillModule } from 'ngx-quill';
import {FileUploadComponent} from 'app/shared/file-upload/file-upload.component';
import { FileUploadModule } from 'ng2-file-upload';

@NgModule({
  imports: [InformaSharedLibsModule, QuillModule.forRoot(), FileUploadModule ],
  declarations: [FindLanguageFromKeyPipe, AlertComponent, AlertErrorComponent, LoginModalComponent, HasAnyAuthorityDirective, QuillEditorCustomComponent, FileUploadComponent],
  entryComponents: [LoginModalComponent],
  exports: [
    InformaSharedLibsModule,
    FindLanguageFromKeyPipe,
    AlertComponent,
    AlertErrorComponent,
    LoginModalComponent,
    HasAnyAuthorityDirective,
    QuillEditorCustomComponent,
    FileUploadComponent
  ]
})
export class InformaSharedModule {}
