import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IGrupo } from 'app/shared/model/grupo.interface';

type EntityResponseType = HttpResponse<IGrupo>;
type EntityArrayResponseType = HttpResponse<IGrupo[]>;

@Injectable({ providedIn: 'root' })
export class GrupoService {
  public resourceUrl = SERVER_API_URL + 'api/grupos';

  constructor(protected http: HttpClient) {}

  create(grupo: IGrupo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(grupo);
    return this.http
      .post<IGrupo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(grupo: IGrupo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(grupo);
    return this.http
      .put<IGrupo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IGrupo>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IGrupo[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(grupo: IGrupo): IGrupo {
    const copy: IGrupo = Object.assign({}, grupo, {
      criacao: grupo.criacao && grupo.criacao.isValid() ? grupo.criacao.toJSON() : undefined,
      ultimaEdicao: grupo.ultimaEdicao && grupo.ultimaEdicao.isValid() ? grupo.ultimaEdicao.toJSON() : undefined
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
      res.body.forEach((grupo: IGrupo) => {
        grupo.criacao = grupo.criacao ? moment(grupo.criacao) : undefined;
        grupo.ultimaEdicao = grupo.ultimaEdicao ? moment(grupo.ultimaEdicao) : undefined;
      });
    }
    return res;
  }
}
