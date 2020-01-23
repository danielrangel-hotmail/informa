import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { InformaSharedModule } from 'app/shared/shared.module';
import { HOME_ROUTE } from './home.route';
import { HomeComponent } from './home.component';
import { InformaSharedPostModule } from 'app/entities/shared-post/informa-shared-post.module';
import { WebNotificationModule } from 'app/entities/web-notification/web-notification.module';

@NgModule({
  imports: [InformaSharedModule, InformaSharedPostModule, WebNotificationModule, RouterModule.forChild([HOME_ROUTE])],
  declarations: [HomeComponent]
})
export class InformaHomeModule {}
