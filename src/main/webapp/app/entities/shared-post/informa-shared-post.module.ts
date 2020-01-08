import { NgModule } from '@angular/core';
import { InformaSharedModule } from 'app/shared/shared.module';
import { PostComponent } from 'app/entities/shared-post/post.component';
import { RouterModule } from '@angular/router';
import { QuillModule } from 'ngx-quill';
import { PostPublicaDialogComponent } from './post-publica-dialog.component';

@NgModule({
  imports: [InformaSharedModule, RouterModule, QuillModule.forRoot()],
  declarations: [PostComponent, PostPublicaDialogComponent],
  exports: [PostComponent],
  entryComponents: [PostPublicaDialogComponent]
})
export class InformaSharedPostModule {}
