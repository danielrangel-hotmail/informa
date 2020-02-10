import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILinkExterno } from '../../shared/model/link-externo.model';

@Component({
  selector: 'jhi-link-externo-detail',
  templateUrl: './link-externo-detail.component.html'
})
export class LinkExternoDetailComponent implements OnInit {
  linkExterno: ILinkExterno | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ linkExterno }) => {
      this.linkExterno = linkExterno;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
