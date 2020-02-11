import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { InformaSharedModule } from 'app/shared/shared.module';
import { PostReacaoComponent } from './post-reacao.component';
import { PostReacaoDetailComponent } from './post-reacao-detail.component';
import { PostReacaoUpdateComponent } from './post-reacao-update.component';
import { PostReacaoDeleteDialogComponent } from './post-reacao-delete-dialog.component';
import { postReacaoRoute } from './post-reacao.route';

@NgModule({
  imports: [InformaSharedModule, RouterModule.forChild(postReacaoRoute)],
  declarations: [PostReacaoComponent, PostReacaoDetailComponent, PostReacaoUpdateComponent, PostReacaoDeleteDialogComponent],
  entryComponents: [PostReacaoDeleteDialogComponent]
})
export class InformaPostReacaoModule {}
