import { NgModule } from '@angular/core';
import { InformaSharedModule } from 'app/shared/shared.module';
import { PostComponent } from './post.component';
import { RouterModule } from '@angular/router';
import { QuillModule } from 'ngx-quill';
import { PostPublicaDialogComponent } from './post-publica-dialog.component';
import { PostSingleComponent } from './post-single/post-single.component';
import { PostImageViewComponent } from './post-image-view/post-image-view.component';
import { NgxGalleryModule } from 'ngx-gallery';

@NgModule({
  imports: [InformaSharedModule, RouterModule, QuillModule.forRoot(), NgxGalleryModule],
  declarations: [PostComponent, PostPublicaDialogComponent, PostSingleComponent, PostImageViewComponent],
  exports: [PostComponent],
  entryComponents: [PostPublicaDialogComponent]
})
export class InformaSharedPostModule {}
