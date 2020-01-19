import {Moment} from 'moment';
import {IArquivo} from 'app/shared/model/arquivo.model';
import {ILinkExterno} from 'app/shared/model/link-externo.model';

export interface IMensagem {
  id?: number;
  versao?: number;
  criacao?: Moment;
  ultimaEdicao?: Moment;
  conteudo?: string;
  temConversa?: boolean;
  arquivos?: IArquivo[];
  linksExternos?: ILinkExterno[];
  autorId?: number;
  postId?: number;
  conversaId?: number;
  autorNome?: string;
}
