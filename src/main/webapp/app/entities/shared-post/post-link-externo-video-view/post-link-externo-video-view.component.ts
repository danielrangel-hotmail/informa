import { Component, OnInit, Input } from '@angular/core';
import {IPost} from 'app/shared/model/post.interface';
import { EmbedVideoService } from 'ngx-embed-video';

@Component({
  selector: 'jhi-post-link-externo-video-view',
  templateUrl: './post-link-externo-video-view.component.html',
  styleUrls: ['./post-link-externo-video-view.component.scss']
})
export class PostLinkExternoVideoViewComponent implements OnInit {
  @Input() post?: IPost;
  iframeHtml: any;

  constructor(protected embedService: EmbedVideoService) { }

  ngOnInit(): void {
    const linkExterno = this.post!.linksExternos!.find( link => link.tipo === "VIDEO");
    if (linkExterno) {
      this.iframeHtml = this.embedService.embed(linkExterno.link ,
        { attr: { width: 558, height: 314 } } );
    } else {
      this.iframeHtml = null;
    }
  }

}
