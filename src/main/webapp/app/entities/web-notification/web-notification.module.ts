import { NgModule } from '@angular/core';
import { WebNotificationComponent } from './web-notification/web-notification.component';
import {InformaSharedModule} from 'app/shared/shared.module';

@NgModule({
  imports: [ InformaSharedModule ],
  exports: [
    WebNotificationComponent
  ],
  declarations: [WebNotificationComponent]
})
export class WebNotificationModule { }
