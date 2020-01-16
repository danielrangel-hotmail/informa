import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { InformaSharedModule } from 'app/shared/shared.module';
import { PostDetailComponent } from './post-detail.component';
import { PostUpdateComponent } from './post-update.component';
import { PostDeleteDialogComponent } from './post-delete-dialog.component';
import { postRoute } from './post.route';
import { MyPostComponent } from './my-post.component';
import { InformaSharedPostModule } from '../shared-post/informa-shared-post.module';
import { PostVideoLinkComponent } from './post-video-link/post-video-link.component';
import { PostImageComponent } from './post-image/post-image.component';
import { FileUploadModule } from 'ng2-file-upload';
import {FileUploadComponent} from 'app/entities/post/file-upload/file-upload.component';
import { NgxGalleryModule } from 'ngx-gallery';
import { EmbedVideo } from 'ngx-embed-video';


@NgModule({
  imports: [InformaSharedModule, InformaSharedPostModule, RouterModule.forChild(postRoute), FileUploadModule, NgxGalleryModule, EmbedVideo],
  declarations: [PostDetailComponent, PostUpdateComponent, PostDeleteDialogComponent, MyPostComponent, PostVideoLinkComponent, PostImageComponent, FileUploadComponent],
  entryComponents: [PostDeleteDialogComponent]
})
export class InformaPostModule {}
