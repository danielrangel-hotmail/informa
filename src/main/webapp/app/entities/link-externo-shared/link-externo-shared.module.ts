import { NgModule } from '@angular/core';
import { InformaSharedModule } from 'app/shared/shared.module';
import {LinkExternoSharedComponent} from 'app/entities/link-externo-shared/link-externo-shared.component';
import { EmbedVideo } from 'ngx-embed-video';

@NgModule({
  declarations: [LinkExternoSharedComponent],
  imports: [
    InformaSharedModule, EmbedVideo
  ],
  exports: [LinkExternoSharedComponent]
})
export class LinkExternoSharedModule { }
