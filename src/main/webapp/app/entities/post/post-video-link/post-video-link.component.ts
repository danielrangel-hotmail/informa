import { Component } from '@angular/core';
import { EmbedVideoService } from 'ngx-embed-video';

@Component({
  selector: 'jhi-post-video-link',
  templateUrl: './post-video-link.component.html',
  styleUrls: ['./post-video-link.component.scss']
})
export class PostVideoLinkComponent  {
  iframeHtml: any;
  vimeoUrl = 'https://vimeo.com/197933516';
  youtubeUrl = 'https://www.youtube.com/watch?v=iHhcHTlGtRs';
  dailymotionUrl =
    'https://www.dailymotion.com/video/x20qnej_red-bull-presents-wild-ride-bmx-mtb-dirt_sport';

  constructor(private embedService: EmbedVideoService) {
    this.embedService.embed(this.vimeoUrl);
    this.iframeHtml = this.embedService.embed(this.youtubeUrl,{
      attr: { width: 600, height: 300 }} );
    this.embedService.embed(this.dailymotionUrl);
  }
}
