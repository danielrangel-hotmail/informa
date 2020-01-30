import { Moment } from 'moment';
import { IPerfilGrupo } from 'app/shared/model/perfil-grupo.model';
import { ITopico } from 'app/shared/model/topico.model';
import { IGrupo } from 'app/shared/model/grupo.interface';

export class Grupo implements IGrupo {
  constructor(
    public id?: number,
    public versao?: number,
    public criacao?: Moment,
    public ultimaEdicao?: Moment,
    public nome?: string,
    public descricao?: string,
    public formal?: boolean,
    public opcional?: boolean,
    public logoContentType?: string,
    public logo?: any,
    public cabecalhoSuperiorCor?: string,
    public cabecalhoInferiorCor?: string,
    public logoFundoCor?: string,
    public usuarios?: IPerfilGrupo[],
    public topicos?: ITopico[]
  ) {
    this.formal = this.formal || false;
    this.opcional = this.opcional || false;
  }
}
