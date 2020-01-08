import { Moment } from 'moment';

export interface IGrupo {
  id?: number;
  versao?: number;
  criacao?: Moment;
  ultimaEdicao?: Moment;
  nome?: string;
  descricao?: string;
  formal?: boolean;
  opcional?: boolean;
}

export class Grupo implements IGrupo {
  constructor(
    public id?: number,
    public versao?: number,
    public criacao?: Moment,
    public ultimaEdicao?: Moment,
    public nome?: string,
    public descricao?: string,
    public formal?: boolean,
    public opcional?: boolean
  ) {
    this.formal = this.formal || false;
    this.opcional = this.opcional || false;
  }
}
