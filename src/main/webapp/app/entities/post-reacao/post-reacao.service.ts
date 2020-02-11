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

type EntityResponseType = HttpResponse<IPostReacao>;
type EntityArrayResponseType = HttpResponse<IPostReacao[]>;

@Injectable({ providedIn: 'root' })
export class PostReacaoService {
  public resourceUrl = SERVER_API_URL + 'api/post-reacaos';

  constructor(protected http: HttpClient) {}

  create(postReacao: IPostReacao): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(postReacao);
    return this.http
      .post<IPostReacao>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(postReacao: IPostReacao): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(postReacao);
    return this.http
      .put<IPostReacao>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
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

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
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
      res.body.criacao = res.body.criacao ? moment(res.body.criacao) : undefined;
      res.body.ultimaEdicao = res.body.ultimaEdicao ? moment(res.body.ultimaEdicao) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((postReacao: IPostReacao) => {
        postReacao.criacao = postReacao.criacao ? moment(postReacao.criacao) : undefined;
        postReacao.ultimaEdicao = postReacao.ultimaEdicao ? moment(postReacao.ultimaEdicao) : undefined;
      });
    }
    return res;
  }
}
