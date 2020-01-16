import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ILinkExterno } from 'app/shared/model/link-externo.model';

type EntityResponseType = HttpResponse<ILinkExterno>;
type EntityArrayResponseType = HttpResponse<ILinkExterno[]>;

@Injectable({ providedIn: 'root' })
export class LinkExternoService {
  public resourceUrl = SERVER_API_URL + 'api/link-externos';

  constructor(protected http: HttpClient) {}

  create(linkExterno: ILinkExterno): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(linkExterno);
    return this.http
      .post<ILinkExterno>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(linkExterno: ILinkExterno): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(linkExterno);
    return this.http
      .put<ILinkExterno>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ILinkExterno>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ILinkExterno[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(linkExterno: ILinkExterno): ILinkExterno {
    const copy: ILinkExterno = Object.assign({}, linkExterno, {
      criacao: linkExterno.criacao && linkExterno.criacao.isValid() ? linkExterno.criacao.toJSON() : undefined,
      ultimaEdicao: linkExterno.ultimaEdicao && linkExterno.ultimaEdicao.isValid() ? linkExterno.ultimaEdicao.toJSON() : undefined
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
      res.body.forEach((linkExterno: ILinkExterno) => {
        linkExterno.criacao = linkExterno.criacao ? moment(linkExterno.criacao) : undefined;
        linkExterno.ultimaEdicao = linkExterno.ultimaEdicao ? moment(linkExterno.ultimaEdicao) : undefined;
      });
    }
    return res;
  }
}
