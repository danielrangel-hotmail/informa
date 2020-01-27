import { Moment } from 'moment';
import { IGrupo } from 'app/shared/model/grupo.model';

export interface IPerfilGrupo {
  id?: number;
  criacao?: Moment;
  ultimaEdicao?: Moment;
  versao?: number;
  favorito?: boolean;
  notifica?: boolean;
  perfilId?: number;
  grupo?: IGrupo;
}

export class PerfilGrupo implements IPerfilGrupo {
  constructor(
    public id?: number,
    public criacao?: Moment,
    public ultimaEdicao?: Moment,
    public versao?: number,
    public favorito?: boolean,
    public notifica?: boolean,
    public perfilId?: number,
    public grupo?: IGrupo
  ) {
    this.favorito = this.favorito || false;
    this.notifica = this.notifica || false;
  }
}
