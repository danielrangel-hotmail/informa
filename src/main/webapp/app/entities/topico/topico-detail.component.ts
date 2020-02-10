import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITopico } from '../../shared/model/topico.model';

@Component({
  selector: 'jhi-topico-detail',
  templateUrl: './topico-detail.component.html'
})
export class TopicoDetailComponent implements OnInit {
  topico: ITopico | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ topico }) => {
      this.topico = topico;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
