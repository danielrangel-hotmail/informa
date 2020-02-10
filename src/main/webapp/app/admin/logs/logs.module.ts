import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { InformaSharedModule } from '../../shared/shared.module';

import { LogsComponent } from './logs.component';

import { logsRoute } from './logs.route';

@NgModule({
  imports: [InformaSharedModule, RouterModule.forChild([logsRoute])],
  declarations: [LogsComponent]
})
export class LogsModule {}
