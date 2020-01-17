import { NgModule } from '@angular/core';
import { InformaSharedModule } from 'app/shared/shared.module';
import { PostComponent } from './post.component';
import { RouterModule } from '@angular/router';
import { QuillModule } from 'ngx-quill';
import { PostPublicaDialogComponent } from './post-publica-dialog.component';
import { PostSingleComponent } from './post-single/post-single.component';
import { PostImageViewComponent } from './post-image-view/post-image-view.component';
import { NgxGalleryModule } from 'ngx-gallery';
import { PostLinkExternoVideoViewComponent } from './post-link-externo-video-view/post-link-externo-video-view.component';
import { EmbedVideo } from 'ngx-embed-video';
import { GravatarModule } from  'ngx-gravatar';

@NgModule({
  imports: [InformaSharedModule, RouterModule, QuillModule.forRoot(), NgxGalleryModule, EmbedVideo, GravatarModule ],
  declarations: [PostComponent, PostPublicaDialogComponent, PostSingleComponent, PostImageViewComponent, PostLinkExternoVideoViewComponent],
  exports: [PostComponent],
  entryComponents: [PostPublicaDialogComponent]
})
export class InformaSharedPostModule {}
