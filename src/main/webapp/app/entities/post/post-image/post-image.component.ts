import { Component, OnInit } from '@angular/core';
import { RouterStateSnapshot, ActivatedRouteSnapshot, ActivatedRoute } from '@angular/router';
import { IPost } from 'app/shared/model/post.model';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

@Component({
  selector: 'jhi-post-image',
  templateUrl: './post-image.component.html',
  styleUrls: ['./post-image.component.scss']
})
export class PostImageComponent implements OnInit {
  private post$?: Observable<IPost>;

  constructor(protected activatedRoute: ActivatedRoute) {

  }

  ngOnInit(): void {
    const routerParent = this.activatedRoute.parent;
    if (routerParent != null) {
      this.post$ = routerParent.data.pipe(map((data) => { return data.post}));
    }

  }

}
