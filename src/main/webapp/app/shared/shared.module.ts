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
import { HoveredToggleComponent } from './hovered-toggle/hovered-toggle.component';
import { AvatarCroppedComponent } from './avatar-cropped/avatar-cropped.component';
import { ColorPickerComponent } from './color-picker/color-picker.component';
import { InsistenceEnvironmentComponent } from './insistence-environment/insistence-environment.component';
import { EmojiListPickerComponent } from './emoji-list-picker/emoji-list-picker.component';
import { HoveredShowComponent } from './hovered-show/hovered-show.component';

@NgModule({
  imports: [InformaSharedLibsModule, QuillModule.forRoot() ],
  declarations: [FindLanguageFromKeyPipe, AlertComponent, AlertErrorComponent, LoginModalComponent, HasAnyAuthorityDirective, QuillEditorCustomComponent, HoveredComponent, HoveredToggleComponent, AvatarCroppedComponent, ColorPickerComponent, InsistenceEnvironmentComponent, EmojiListPickerComponent, HoveredShowComponent],
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
    AvatarCroppedComponent,
    ColorPickerComponent,
    EmojiListPickerComponent,
    HoveredShowComponent
  ]
})
export class InformaSharedModule {}
