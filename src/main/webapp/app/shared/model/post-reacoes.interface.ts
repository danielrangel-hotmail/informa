import { IPostReacao } from 'app/shared/model/post-reacao.model';

export interface IPostReacoes {
  reacoes?: IPostReacao[],
  reacaoLogado?: IPostReacao
}
