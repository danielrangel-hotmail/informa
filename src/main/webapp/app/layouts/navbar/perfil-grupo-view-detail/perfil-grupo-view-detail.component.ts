import { Component, EventEmitter, Input, Output } from '@angular/core';
import { IPerfilGrupo } from 'app/shared/model/perfil-grupo.interface';

@Component({
  selector: 'jhi-perfil-grupo-view-detail',
  templateUrl: './perfil-grupo-view-detail.component.html',
  styleUrls: ['./perfil-grupo-view-detail.component.scss']
})
export class PerfilGrupoViewDetailComponent {
  @Input() perfilGrupo!: IPerfilGrupo;
  @Output() toggleNotifica: EventEmitter<IPerfilGrupo> = new EventEmitter();
  @Output() toggleFavorito: EventEmitter<IPerfilGrupo> = new EventEmitter();
  @Output() toggleModerador: EventEmitter<IPerfilGrupo> = new EventEmitter();

  constructor() { }

  toggleNotificaEmit(): void {
    this.toggleNotifica.emit(this.perfilGrupo);
  }

  toggleFavoritoEmit(): void {
    this.toggleFavorito.emit(this.perfilGrupo);
  }

  toggleModeradorEmit(): void {
    this.toggleModerador.emit(this.perfilGrupo);
  }

}
