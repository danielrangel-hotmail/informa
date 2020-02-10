import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { InformaSharedModule } from '../shared/shared.module';
import { HOME_ROUTE } from './home.route';
import { HomeComponent } from './home.component';
import { InformaSharedPostModule } from '../entities/shared-post/informa-shared-post.module';
import { WebNotificationModule } from '../entities/web-notification/web-notification.module';

@NgModule({
  imports: [InformaSharedModule, InformaSharedPostModule, WebNotificationModule, RouterModule.forChild([HOME_ROUTE])],
  declarations: [HomeComponent]
})
export class InformaHomeModule {}
