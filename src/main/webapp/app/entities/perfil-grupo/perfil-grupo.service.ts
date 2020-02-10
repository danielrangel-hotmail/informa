import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from '../../shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPerfilGrupo } from '../../shared/model/perfil-grupo.interface';

type EntityResponseType = HttpResponse<IPerfilGrupo>;
type EntityArrayResponseType = HttpResponse<IPerfilGrupo[]>;

@Injectable({ providedIn: 'root' })
export class PerfilGrupoService {
  public resourceUrl = SERVER_API_URL + 'api/perfil-grupos';

  constructor(protected http: HttpClient) {}

  create(perfilGrupo: IPerfilGrupo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(perfilGrupo);
    return this.http
      .post<IPerfilGrupo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(perfilGrupo: IPerfilGrupo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(perfilGrupo);
    return this.http
      .put<IPerfilGrupo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPerfilGrupo>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPerfilGrupo[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  queryManagement(): Observable<EntityArrayResponseType> {
    return this.http
      .get<IPerfilGrupo[]>(`${this.resourceUrl}-management`, {  observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(perfilGrupo: IPerfilGrupo): IPerfilGrupo {
    const copy: IPerfilGrupo = Object.assign({}, perfilGrupo, {
      criacao: perfilGrupo.criacao && perfilGrupo.criacao.isValid() ? perfilGrupo.criacao.toJSON() : undefined,
      ultimaEdicao: perfilGrupo.ultimaEdicao && perfilGrupo.ultimaEdicao.isValid() ? perfilGrupo.ultimaEdicao.toJSON() : undefined
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
      res.body.forEach((perfilGrupo: IPerfilGrupo) => {
        perfilGrupo.criacao = perfilGrupo.criacao ? moment(perfilGrupo.criacao) : undefined;
        perfilGrupo.ultimaEdicao = perfilGrupo.ultimaEdicao ? moment(perfilGrupo.ultimaEdicao) : undefined;
      });
    }
    return res;
  }

  public perfisOrdenados(perfis: IPerfilGrupo[]): IPerfilGrupo[] {
    return perfis.sort(
      (a, b) => (
        (a.grupo!.opcional!) === (b.grupo!.opcional!) ? (a.grupo!.nome!.localeCompare(b.grupo!.nome!)) : ((a.grupo!.opcional!) ? 1: -1)
      )
    );
  }

  public perfisOrdenadosEExistentes(perfis: IPerfilGrupo[]): IPerfilGrupo[] {
    return this.perfisOrdenados(perfis.filter(perfil => perfil.id != null));
  }

}
