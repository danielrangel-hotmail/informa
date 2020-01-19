import {Moment} from 'moment';
import {IArquivo} from 'app/shared/model/arquivo.model';
import {ILinkExterno} from 'app/shared/model/link-externo.model';
import {IMensagem} from 'app/shared/model/mensagem.interface';

export class Mensagem implements IMensagem {
  constructor(
    public id?: number,
    public versao?: number,
    public criacao?: Moment,
    public ultimaEdicao?: Moment,
    public conteudo?: string,
    public temConversa?: boolean,
    public arquivos?: IArquivo[],
    public linksExternos?: ILinkExterno[],
    public autorId?: number,
    public postId?: number,
    public conversaId?: number,
    public autorNome?: string,
  ) {
    this.temConversa = this.temConversa || false;
  }
}
