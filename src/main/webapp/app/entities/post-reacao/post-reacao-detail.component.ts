import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPostReacao } from 'app/shared/model/post-reacao.model';

@Component({
  selector: 'jhi-post-reacao-detail',
  templateUrl: './post-reacao-detail.component.html'
})
export class PostReacaoDetailComponent implements OnInit {
  postReacao: IPostReacao | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ postReacao }) => {
      this.postReacao = postReacao;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
