import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import {IMensagem} from 'app/shared/model/mensagem.interface';

type EntityResponseType = HttpResponse<IMensagem>;
type EntityArrayResponseType = HttpResponse<IMensagem[]>;

@Injectable({ providedIn: 'root' })
export class MensagemService {
  public resourceUrl = SERVER_API_URL + 'api/mensagems';

  constructor(protected http: HttpClient) {}

  create(mensagem: IMensagem): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(mensagem);
    return this.http
      .post<IMensagem>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(mensagem: IMensagem): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(mensagem);
    return this.http
      .put<IMensagem>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IMensagem>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(postId: number, req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IMensagem[]>(`${this.resourceUrl}/post/${postId}`, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(mensagem: IMensagem): IMensagem {
    const copy: IMensagem = Object.assign({}, mensagem, {
      criacao: mensagem.criacao && mensagem.criacao.isValid() ? mensagem.criacao.toJSON() : undefined,
      ultimaEdicao: mensagem.ultimaEdicao && mensagem.ultimaEdicao.isValid() ? mensagem.ultimaEdicao.toJSON() : undefined
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
      res.body.forEach((mensagem: IMensagem) => {
        mensagem.criacao = mensagem.criacao ? moment(mensagem.criacao) : undefined;
        mensagem.ultimaEdicao = mensagem.ultimaEdicao ? moment(mensagem.ultimaEdicao) : undefined;
      });
    }
    return res;
  }
}
