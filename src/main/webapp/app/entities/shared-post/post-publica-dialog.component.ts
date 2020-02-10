import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPost } from '../../shared/model/post.interface';
import { PostService } from '../shared-post/post.service';
// import { MatomoTracker } from 'ngx-matomo';

@Component({
  templateUrl: './post-publica-dialog.component.html'
})
export class PostPublicaDialogComponent {
  post?: IPost;

  constructor(protected postService: PostService,
              public activeModal: NgbActiveModal,
              protected eventManager: JhiEventManager,
              // protected matomoTracker: MatomoTracker
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmPublicacao(): void {
    if (this.post != null) {
      this.postService.publica(this.post).subscribe(() => {
        this.eventManager.broadcast('postListModification');
        this.analyticsEvent();
        this.activeModal.close();
      });
    }
  }

  analyticsEvent(): void {
    // let action = 'publicacao'
    // if (this.post!.arquivos!.length > 0) action = action + '_foto';
    // if (this.post!.linksExternos!.length > 0) action = action + '_video';
    // this.matomoTracker.trackEvent('Post', action, this.post!.grupoId!.toString(), this.post!.id);

  }
}
