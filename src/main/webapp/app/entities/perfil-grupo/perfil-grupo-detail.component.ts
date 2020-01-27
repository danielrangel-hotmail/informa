import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPerfilGrupo } from 'app/shared/model/perfil-grupo.model';

@Component({
  selector: 'jhi-perfil-grupo-detail',
  templateUrl: './perfil-grupo-detail.component.html'
})
export class PerfilGrupoDetailComponent implements OnInit {
  perfilGrupo: IPerfilGrupo | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ perfilGrupo }) => {
      this.perfilGrupo = perfilGrupo;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
