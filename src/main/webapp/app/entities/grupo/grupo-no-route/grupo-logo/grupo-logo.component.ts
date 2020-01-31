import { Component, Input } from '@angular/core';
import { IGrupo } from 'app/shared/model/grupo.interface';

@Component({
  selector: 'jhi-grupo-logo',
  templateUrl: './grupo-logo.component.html',
  styleUrls: ['./grupo-logo.component.scss']
})
export class GrupoLogoComponent  {
  @Input() grupo!: IGrupo;
  @Input() size!: number
  constructor() { }

}
