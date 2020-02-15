import { NgModule } from '@angular/core';
import { InformaSharedModule } from 'app/shared/shared.module';
import { PostComponent } from './post.component';
import { RouterModule } from '@angular/router';
import { QuillModule } from 'ngx-quill';
import { PostPublicaDialogComponent } from './post-publica-dialog.component';
import { PostSingleComponent } from './post-single/post-single.component';
import { PostImageViewComponent } from './post-image-view/post-image-view.component';
import { PostLinkExternoVideoViewComponent } from './post-link-externo-video-view/post-link-externo-video-view.component';
import { EmbedVideo } from 'ngx-embed-video';
import { GravatarModule } from  'ngx-gravatar';
import { PostDeleteDialogComponent } from 'app/entities/shared-post/post-delete-dialog.component';
import { GrupoNoRouteModule } from 'app/entities/grupo/grupo-no-route/grupo-no-route.module';
import { PostReacoesComponent } from './post-reacoes/post-reacoes.component';
import { NgxGalleryModule } from 'app/ngx-gallery';

@NgModule({
  imports: [InformaSharedModule, RouterModule, QuillModule.forRoot(), EmbedVideo, GravatarModule, GrupoNoRouteModule, NgxGalleryModule],
  declarations: [PostComponent, PostPublicaDialogComponent, PostDeleteDialogComponent, PostSingleComponent, PostImageViewComponent, PostLinkExternoVideoViewComponent, PostReacoesComponent],
  exports: [PostComponent, PostSingleComponent],
  entryComponents: [PostPublicaDialogComponent, PostDeleteDialogComponent]
})
export class InformaSharedPostModule {}
