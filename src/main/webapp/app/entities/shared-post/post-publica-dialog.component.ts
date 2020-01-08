import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPost } from 'app/shared/model/post.model';
import { PostService } from '../shared-post/post.service';

@Component({
  templateUrl: './post-publica-dialog.component.html'
})
export class PostPublicaDialogComponent {
  post?: IPost;

  constructor(protected postService: PostService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmPublicacao(): void {
    if (this.post != null) {
      this.postService.publica(this.post).subscribe(() => {
        this.eventManager.broadcast('postListModification');
        this.activeModal.close();
      });
    }
  }
}
