import { Component, Input, OnInit } from '@angular/core';
import { IGrupo } from 'app/shared/model/grupo.interface';

@Component({
  selector: 'jhi-grupo-testa',
  templateUrl: './grupo-testa.component.html',
  styleUrls: ['./grupo-testa.component.scss']
})
export class GrupoTestaComponent implements OnInit {
  @Input() grupo!: IGrupo;

  constructor() { }

  ngOnInit(): void {
  }

}
