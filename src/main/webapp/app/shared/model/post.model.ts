import { Moment } from 'moment';

export interface IPost {
  id?: number;
  versao?: number;
  criacao?: Moment;
  ultimaEdicao?: Moment;
  conteudo?: string;
  publicacao?: Moment;
  autorId?: number;
  grupoId?: number;
  autorNome?: string;
  grupoNome?: string;
}

export class Post implements IPost {
  constructor(
    public id?: number,
    public versao?: number,
    public criacao?: Moment,
    public ultimaEdicao?: Moment,
    public conteudo?: string,
    public publicacao?: Moment,
    public autorId?: number,
    public grupoId?: number,
    public autorNome?: string,
    public grupoNome?: string

  ) {}
}
