import { Moment } from 'moment';
import { IArquivo } from '../../shared/model/arquivo.model';
import { ILinkExterno } from '../../shared/model/link-externo.model';
import { IPostReacoes } from 'app/shared/model/post-reacoes.interface';

export interface IPost {
  id?: number;
  versao?: number;
  criacao?: Moment;
  ultimaEdicao?: Moment;
  conteudo?: string;
  oficial?: boolean;
  publicacao?: Moment;
  arquivos?: IArquivo[];
  linksExternos?: ILinkExterno[];
  autorId?: number;
  grupoId?: number;
  numeroDeMensagens?: number;
  autorNome?: string;
  autorEmail?: string;
  grupoNome?: string;
  reacoes?: IPostReacoes;
  removido?: boolean;
  removedorId?: number;
  arquivado?: boolean;
  arquivadorId?: number;
  momentoRemocao?: Moment;
  momentoArquivado?: Moment;


}
