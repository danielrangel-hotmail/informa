import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPerfilUsuario } from 'app/shared/model/perfil-usuario.model';
import { PerfilUsuarioService } from './perfil-usuario.service';

@Component({
  templateUrl: './perfil-usuario-delete-dialog.component.html'
})
export class PerfilUsuarioDeleteDialogComponent {
  perfilUsuario?: IPerfilUsuario;

  constructor(
    protected perfilUsuarioService: PerfilUsuarioService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.perfilUsuarioService.delete(id).subscribe(() => {
      this.eventManager.broadcast('perfilUsuarioListModification');
      this.activeModal.close();
    });
  }
}
