import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PerfilGrupoService } from './perfil-grupo.service';
import { IPerfilGrupo } from 'app/shared/model/perfil-grupo.interface';

@Component({
  templateUrl: './perfil-grupo-delete-dialog.component.html'
})
export class PerfilGrupoDeleteDialogComponent {
  perfilGrupo?: IPerfilGrupo;

  constructor(
    protected perfilGrupoService: PerfilGrupoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.perfilGrupoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('perfilGrupoListModification');
      this.activeModal.close();
    });
  }
}
