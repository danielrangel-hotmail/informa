import { Moment } from 'moment';

export interface IArquivo {
  id?: number;
  versao?: number;
  criacao?: Moment;
  ultimaEdicao?: Moment;
  nome?: string;
  colecao?: string;
  tipo?: string;
  link?: string;
  uploadConfirmado?: boolean;
  usuarioId?: number;
  postId?: number;
  mensagemId?: number;
}

export class Arquivo implements IArquivo {
  constructor(
    public id?: number,
    public versao?: number,
    public criacao?: Moment,
    public ultimaEdicao?: Moment,
    public nome?: string,
    public colecao?: string,
    public tipo?: string,
    public link?: string,
    public uploadConfirmado?: boolean,
    public usuarioId?: number,
    public postId?: number,
    public mensagemId?: number
  ) {
    this.uploadConfirmado = this.uploadConfirmado || false;
  }
}
