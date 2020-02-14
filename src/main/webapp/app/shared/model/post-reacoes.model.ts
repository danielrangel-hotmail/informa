import { IPostReacoes } from 'app/shared/model/post-reacoes.interface';
import { IPostReacao } from 'app/shared/model/post-reacao.model';

export class  PostReacoes implements IPostReacoes {
  constructor(
    reacoes?: IPostReacao[],
    reacaoLogado?: IPostReacao
  ) {
  }
}
