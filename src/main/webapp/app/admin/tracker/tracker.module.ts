import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { InformaSharedModule } from '../../shared/shared.module';

import { TrackerComponent } from './tracker.component';

import { trackerRoute } from './tracker.route';

@NgModule({
  imports: [InformaSharedModule, RouterModule.forChild([trackerRoute])],
  declarations: [TrackerComponent]
})
export class TrackerModule {}
