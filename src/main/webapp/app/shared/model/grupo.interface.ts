import { Moment } from 'moment';
import { ITopico } from 'app/shared/model/topico.model';
import { IPerfilGrupo } from 'app/shared/model/perfil-grupo.interface';
import { ISimpleUser } from 'app/shared/model/simples-user.interface';

export interface IGrupo {
  id?: number;
  versao?: number;
  criacao?: Moment;
  ultimaEdicao?: Moment;
  nome?: string;
  descricao?: string;
  formal?: boolean;
  opcional?: boolean;
  logoContentType?: string;
  logo?: any;
  cabecalhoSuperiorCor?: string;
  cabecalhoInferiorCor?: string;
  logoFundoCor?: string;
  usuarios?: IPerfilGrupo[];
  topicos?: ITopico[];
  moderadores?: ISimpleUser[];
}
