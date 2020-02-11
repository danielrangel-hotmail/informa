import { Moment } from 'moment';

export interface IPostReacao {
  id?: number;
  criacao?: Moment;
  ultimaEdicao?: Moment;
  versao?: number;
  reacao?: string;
  perfilId?: number;
  postId?: number;
}

export class PostReacao implements IPostReacao {
  constructor(
    public id?: number,
    public criacao?: Moment,
    public ultimaEdicao?: Moment,
    public versao?: number,
    public reacao?: string,
    public perfilId?: number,
    public postId?: number
  ) {}
}
