import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { InformaSharedModule } from '../../shared/shared.module';

import { MetricsComponent } from './metrics.component';

import { metricsRoute } from './metrics.route';

@NgModule({
  imports: [InformaSharedModule, RouterModule.forChild([metricsRoute])],
  declarations: [MetricsComponent]
})
export class MetricsModule {}
