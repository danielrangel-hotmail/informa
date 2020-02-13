import { Moment } from 'moment';
import { IArquivo } from '../../shared/model/arquivo.model';
import { ILinkExterno } from '../../shared/model/link-externo.model';
import {IPost} from '../../shared/model/post.interface';
import { IPostReacao } from 'app/shared/model/post-reacao.model';


export class Post implements IPost {
  constructor(
    public id?: number,
    public versao?: number,
    public criacao?: Moment,
    public ultimaEdicao?: Moment,
    public conteudo?: string,
    public oficial?: boolean,
    public publicacao?: Moment,
    public arquivos?: IArquivo[],
    public linksExternos?: ILinkExterno[],
    public reacoes?: IPostReacao[],
    public autorId?: number,
    public numeroDeMensagens?: number,
    public autorNome?: string,
    public autorEmail?: string,
    public grupoId?: number,
    public reacaoLogado?: IPostReacao
  ) {
    this.oficial = this.oficial || false;
  }
}
