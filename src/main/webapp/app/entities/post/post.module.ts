import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { InformaSharedModule } from 'app/shared/shared.module';
import { PostDetailComponent } from './post-detail.component';
import { PostUpdateComponent } from './post-update.component';
import { postRoute } from './post.route';
import { InformaSharedPostModule } from '../shared-post/informa-shared-post.module';
import { PostVideoLinkComponent } from './post-video-link/post-video-link.component';
import { PostImageComponent } from './post-image/post-image.component';
import { FileUploadModule } from 'ng2-file-upload';
import {FileUploadComponent} from 'app/entities/post/file-upload/file-upload.component';
import { NgxGalleryModule } from 'ngx-gallery';
import { EmbedVideo } from 'ngx-embed-video';
import { LinkExternoSharedModule} from 'app/entities/link-externo-shared/link-externo-shared.module';



@NgModule({
  imports: [InformaSharedModule, InformaSharedPostModule, RouterModule.forChild(postRoute), FileUploadModule, NgxGalleryModule, EmbedVideo, LinkExternoSharedModule],
  declarations: [PostDetailComponent, PostUpdateComponent, PostVideoLinkComponent, PostImageComponent, FileUploadComponent]
})
export class InformaPostModule {}
