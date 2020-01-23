import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPerfilUsuario } from 'app/shared/model/perfil-usuario.model';

type EntityResponseType = HttpResponse<IPerfilUsuario>;
type EntityArrayResponseType = HttpResponse<IPerfilUsuario[]>;

@Injectable({ providedIn: 'root' })
export class PerfilUsuarioService {
  public resourceUrl = SERVER_API_URL + 'api/perfil-usuarios';

  constructor(protected http: HttpClient) {}

  create(perfilUsuario: IPerfilUsuario): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(perfilUsuario);
    return this.http
      .post<IPerfilUsuario>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(perfilUsuario: IPerfilUsuario): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(perfilUsuario);
    return this.http
      .put<IPerfilUsuario>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPerfilUsuario>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPerfilUsuario[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(perfilUsuario: IPerfilUsuario): IPerfilUsuario {
    const copy: IPerfilUsuario = Object.assign({}, perfilUsuario, {
      criacao: perfilUsuario.criacao && perfilUsuario.criacao.isValid() ? perfilUsuario.criacao.toJSON() : undefined,
      ultimaEdicao: perfilUsuario.ultimaEdicao && perfilUsuario.ultimaEdicao.isValid() ? perfilUsuario.ultimaEdicao.toJSON() : undefined,
      entradaNaEmpresa:
        perfilUsuario.entradaNaEmpresa && perfilUsuario.entradaNaEmpresa.isValid()
          ? perfilUsuario.entradaNaEmpresa.format(DATE_FORMAT)
          : undefined,
      saidaDaEmpresa:
        perfilUsuario.saidaDaEmpresa && perfilUsuario.saidaDaEmpresa.isValid()
          ? perfilUsuario.saidaDaEmpresa.format(DATE_FORMAT)
          : undefined,
      nascimento: perfilUsuario.nascimento && perfilUsuario.nascimento.isValid() ? perfilUsuario.nascimento.format(DATE_FORMAT) : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.criacao = res.body.criacao ? moment(res.body.criacao) : undefined;
      res.body.ultimaEdicao = res.body.ultimaEdicao ? moment(res.body.ultimaEdicao) : undefined;
      res.body.entradaNaEmpresa = res.body.entradaNaEmpresa ? moment(res.body.entradaNaEmpresa) : undefined;
      res.body.saidaDaEmpresa = res.body.saidaDaEmpresa ? moment(res.body.saidaDaEmpresa) : undefined;
      res.body.nascimento = res.body.nascimento ? moment(res.body.nascimento) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((perfilUsuario: IPerfilUsuario) => {
        perfilUsuario.criacao = perfilUsuario.criacao ? moment(perfilUsuario.criacao) : undefined;
        perfilUsuario.ultimaEdicao = perfilUsuario.ultimaEdicao ? moment(perfilUsuario.ultimaEdicao) : undefined;
        perfilUsuario.entradaNaEmpresa = perfilUsuario.entradaNaEmpresa ? moment(perfilUsuario.entradaNaEmpresa) : undefined;
        perfilUsuario.saidaDaEmpresa = perfilUsuario.saidaDaEmpresa ? moment(perfilUsuario.saidaDaEmpresa) : undefined;
        perfilUsuario.nascimento = perfilUsuario.nascimento ? moment(perfilUsuario.nascimento) : undefined;
      });
    }
    return res;
  }
}
