import { Moment } from 'moment';
import { LinkTipo } from 'app/shared/model/enumerations/link-tipo.model';

export interface ILinkExterno {
  id?: number;
  versao?: number;
  criacao?: Moment;
  ultimaEdicao?: Moment;
  tipo?: LinkTipo;
  link?: string;
  usuarioId?: number;
  postId?: number;
  mensagemId?: number;
}

export class LinkExterno implements ILinkExterno {
  constructor(
    public id?: number,
    public versao?: number,
    public criacao?: Moment,
    public ultimaEdicao?: Moment,
    public tipo?: LinkTipo,
    public link?: string,
    public usuarioId?: number,
    public postId?: number,
    public mensagemId?: number
  ) {}
}
