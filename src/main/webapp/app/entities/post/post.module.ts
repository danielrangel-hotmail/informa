import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { InformaSharedModule } from 'app/shared/shared.module';
import { PostDetailComponent } from './post-detail.component';
import { PostUpdateComponent } from './post-update.component';
import { PostDeleteDialogComponent } from './post-delete-dialog.component';
import { postRoute } from './post.route';
import { MyPostComponent } from './my-post.component';
import { InformaSharedPostModule } from '../shared-post/informa-shared-post.module';

@NgModule({
  imports: [InformaSharedModule, InformaSharedPostModule, RouterModule.forChild(postRoute)],
  declarations: [PostDetailComponent, PostUpdateComponent, PostDeleteDialogComponent, MyPostComponent],
  entryComponents: [PostDeleteDialogComponent]
})
export class InformaPostModule {}
