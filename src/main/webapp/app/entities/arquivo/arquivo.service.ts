import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map, shareReplay } from 'rxjs/operators';
import * as moment from 'moment';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IArquivo } from 'app/shared/model/arquivo.model';

type EntityResponseType = HttpResponse<IArquivo>;
type EntityArrayResponseType = HttpResponse<IArquivo[]>;
interface IBucketURL {
  url?: string;
}

@Injectable({ providedIn: 'root' })
export class ArquivoService {
  public resourceUrl = SERVER_API_URL + 'api/arquivos';
  public arquivosURL = "";
  public bucketUrl$: Observable<string>;

  constructor(protected http: HttpClient) {
    this.bucketUrl$ = this.getBucketUrl$();
  }

  getBucketUrl$(): Observable<string> {
    return this.http
      .get<IBucketURL>(`${this.resourceUrl}/bucket-url`, { observe: 'response' })
      .pipe(
        map((res: HttpResponse<IBucketURL>) => res.body ? res.body.url! : ""),
        shareReplay());
  }

  create(arquivo: IArquivo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(arquivo);
    return this.http
      .post<IArquivo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(arquivo: IArquivo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(arquivo);
    return this.http
      .put<IArquivo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IArquivo>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IArquivo[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(arquivo: IArquivo): IArquivo {
    const copy: IArquivo = Object.assign({}, arquivo, {
      criacao: arquivo.criacao && arquivo.criacao.isValid() ? arquivo.criacao.toJSON() : undefined,
      ultimaEdicao: arquivo.ultimaEdicao && arquivo.ultimaEdicao.isValid() ? arquivo.ultimaEdicao.toJSON() : undefined
    });
    return copy;
  }

  public convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.criacao = res.body.criacao ? moment(res.body.criacao) : undefined;
      res.body.ultimaEdicao = res.body.ultimaEdicao ? moment(res.body.ultimaEdicao) : undefined;
    }
    return res;
  }

  public convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((arquivo: IArquivo) => {
        arquivo.criacao = arquivo.criacao ? moment(arquivo.criacao) : undefined;
        arquivo.ultimaEdicao = arquivo.ultimaEdicao ? moment(arquivo.ultimaEdicao) : undefined;
      });
    }
    return res;
  }
}
