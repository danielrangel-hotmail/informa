import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { InformaSharedModule } from 'app/shared/shared.module';
import { LinkExternoComponent } from './link-externo.component';
import { LinkExternoDetailComponent } from './link-externo-detail.component';
import { LinkExternoUpdateComponent } from './link-externo-update.component';
import { LinkExternoDeleteDialogComponent } from './link-externo-delete-dialog.component';
import { linkExternoRoute } from './link-externo.route';

@NgModule({
  imports: [InformaSharedModule, RouterModule.forChild(linkExternoRoute)],
  declarations: [LinkExternoComponent, LinkExternoDetailComponent, LinkExternoUpdateComponent, LinkExternoDeleteDialogComponent],
  entryComponents: [LinkExternoDeleteDialogComponent]
})
export class InformaLinkExternoModule {}
