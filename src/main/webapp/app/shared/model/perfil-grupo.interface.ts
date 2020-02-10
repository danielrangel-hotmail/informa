import { Moment } from 'moment';
import { IGrupo } from '../../shared/model/grupo.interface';

export interface IPerfilGrupo {
  id?: number;
  criacao?: Moment;
  ultimaEdicao?: Moment;
  versao?: number;
  favorito?: boolean;
  notifica?: boolean;
  moderador?: boolean;
  perfilId?: number;
  grupo?: IGrupo;
}
