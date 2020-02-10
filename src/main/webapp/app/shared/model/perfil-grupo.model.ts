import { Moment } from 'moment';
import { IGrupo } from '../../shared/model/grupo.interface';
import { IPerfilGrupo } from '../../shared/model/perfil-grupo.interface';

export class PerfilGrupo implements IPerfilGrupo {
  constructor(
    public id?: number,
    public criacao?: Moment,
    public ultimaEdicao?: Moment,
    public versao?: number,
    public favorito?: boolean,
    public notifica?: boolean,
    public moderador?: boolean,
    public perfilId?: number,
    public grupo?: IGrupo
  ) {
    this.favorito = this.favorito || false;
    this.notifica = this.notifica || false;
    this.moderador = this.moderador || false;
  }
}
