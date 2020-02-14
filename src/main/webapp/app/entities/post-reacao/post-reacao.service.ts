import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPostReacao } from 'app/shared/model/post-reacao.model';
import { IPostReacoes } from 'app/shared/model/post-reacoes.interface';

type EntityResponseType = HttpResponse<IPostReacao>;
type EntityArrayResponseType = HttpResponse<IPostReacao[]>;

@Injectable({ providedIn: 'root' })
export class PostReacaoService {
  public resourceUrl = SERVER_API_URL + 'api/post-reacaos';

  constructor(protected http: HttpClient) {}

  create(postReacao: IPostReacao): Observable<HttpResponse<IPostReacoes>> {
    const copy = this.convertDateFromClient(postReacao);
    return this.http
      .post<IPostReacoes>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: HttpResponse<IPostReacoes>) => this.convertDateFromServerReacoes(res)));
  }

  update(postReacao: IPostReacao): Observable<HttpResponse<IPostReacoes>> {
    const copy = this.convertDateFromClient(postReacao);
    return this.http
      .put<IPostReacoes>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: HttpResponse<IPostReacoes>) => this.convertDateFromServerReacoes(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPostReacao>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPostReacao[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<IPostReacoes>> {
    return this.http.delete<IPostReacoes>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: HttpResponse<IPostReacoes>) => this.convertDateFromServerReacoes(res)));
  }

  protected convertDateFromClient(postReacao: IPostReacao): IPostReacao {
    const copy: IPostReacao = Object.assign({}, postReacao, {
      criacao: postReacao.criacao && postReacao.criacao.isValid() ? postReacao.criacao.toJSON() : undefined,
      ultimaEdicao: postReacao.ultimaEdicao && postReacao.ultimaEdicao.isValid() ? postReacao.ultimaEdicao.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      this.convertDateFromServerPostReacao(res.body);
    }
    return res;
  }

  protected convertDateFromServerReacoes(res: HttpResponse<IPostReacoes>): HttpResponse<IPostReacoes> {
    if (res.body) {
      if (res.body.reacaoLogado) this.convertDateFromServerPostReacao(res.body.reacaoLogado);
      res.body.reacoes!.forEach((reacao) => this.convertDateFromServerPostReacao(reacao));
    }
    return res;
  }

  public convertDateFromServerPostReacao(res: IPostReacao): IPostReacao {
    if (res) {
      res.criacao = res.criacao ? moment(res.criacao) : undefined;
      res.ultimaEdicao = res.ultimaEdicao ? moment(res.ultimaEdicao) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((postReacao: IPostReacao) => {
        this.convertDateFromServerPostReacao(postReacao);
      });
    }
    return res;
  }
}
