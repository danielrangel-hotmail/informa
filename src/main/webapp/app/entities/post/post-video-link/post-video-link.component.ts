import { Component, OnInit } from '@angular/core';
import {IPost} from 'app/shared/model/post.interface';
import { Observable } from 'rxjs';
import { ActivatedRoute } from '@angular/router';
import { map } from 'rxjs/operators';

@Component({
  selector: 'jhi-post-video-link',
  templateUrl: './post-video-link.component.html',
  styleUrls: ['./post-video-link.component.scss']
})
export class PostVideoLinkComponent implements OnInit {
  post$?: Observable<IPost>;

  constructor(protected activatedRoute: ActivatedRoute) {
  }

  ngOnInit(): void {
    const routerParent = this.activatedRoute.parent;
    if (routerParent != null) {
      this.post$ = routerParent.data
        .pipe(map((data) => {return data.post}));
    }
  }
}
