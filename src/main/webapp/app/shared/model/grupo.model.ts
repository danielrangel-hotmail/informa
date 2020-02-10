import { Moment } from 'moment';
import { ITopico } from '../../shared/model/topico.model';
import { IGrupo } from '../../shared/model/grupo.interface';
import { IPerfilGrupo } from '../../shared/model/perfil-grupo.interface';
import { ISimpleUser } from '../../shared/model/simples-user.interface';

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
    public topicos?: ITopico[],
    public moderadores?: ISimpleUser[]
  ) {
    this.formal = this.formal || false;
    this.opcional = this.opcional || false;
  }
}
