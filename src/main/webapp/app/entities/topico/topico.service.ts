import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITopico } from 'app/shared/model/topico.model';

type EntityResponseType = HttpResponse<ITopico>;
type EntityArrayResponseType = HttpResponse<ITopico[]>;

@Injectable({ providedIn: 'root' })
export class TopicoService {
  public resourceUrl = SERVER_API_URL + 'api/topicos';

  constructor(protected http: HttpClient) {}

  create(topico: ITopico): Observable<EntityResponseType> {
    return this.http.post<ITopico>(this.resourceUrl, topico, { observe: 'response' });
  }

  update(topico: ITopico): Observable<EntityResponseType> {
    return this.http.put<ITopico>(this.resourceUrl, topico, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITopico>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITopico[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
