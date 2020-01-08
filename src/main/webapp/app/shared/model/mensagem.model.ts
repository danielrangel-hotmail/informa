import { Moment } from 'moment';

export interface IMensagem {
  id?: number;
  versao?: number;
  criacao?: Moment;
  ultimaEdicao?: Moment;
  conteudo?: string;
  temConversa?: boolean;
  autorId?: number;
  postId?: number;
  conversaId?: number;
  autorNome?: string;
}

export class Mensagem implements IMensagem {
  constructor(
    public id?: number,
    public versao?: number,
    public criacao?: Moment,
    public ultimaEdicao?: Moment,
    public conteudo?: string,
    public temConversa?: boolean,
    public autorId?: number,
    public postId?: number,
    public conversaId?: number,
    public autorNome?: string,
  ) {
    this.temConversa = this.temConversa || false;
  }
}
