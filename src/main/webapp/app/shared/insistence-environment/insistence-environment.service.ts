import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { IInsistenceEnvironment } from './insistence.environment.interface';
import { SERVER_API_URL } from '../../app.constants';
import { IInsistenceEnvironmentStatus } from '../../shared/insistence-environment/insistence-environment-status.interface';

type EntityResponseType = HttpResponse<IInsistenceEnvironment>;
type EntityArrayResponseType = HttpResponse<IInsistenceEnvironment[]>;

@Injectable({
  providedIn: 'root'
})
export class InsistenceEnvironmentService {

  public resourceUrl = SERVER_API_URL + 'api/insistence-environment';

  constructor(protected http: HttpClient) {}

  getEnvironments(): Observable<IInsistenceEnvironment[]> {
    return this.http
      .get<IInsistenceEnvironment[]>(this.resourceUrl, { observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => res.body ? res.body : [] ));
  }

  setEnvironment(environment: IInsistenceEnvironment | undefined): Observable<IInsistenceEnvironmentStatus> {
    if (environment === undefined) return of({status: 'OK'});
    return this.http
      .get<IInsistenceEnvironmentStatus>(`${this.resourceUrl}/set/${environment.name}`, { observe: 'response' })
      .pipe(map((res) => res.body ? res.body : {status: 'ZUOU'}));


  }

  resetEnvironments(): Observable<IInsistenceEnvironmentStatus> {
    return this.http
      .get(`${this.resourceUrl}/resetAll`, { observe: 'response' })
      .pipe(map((res) => res.body ? res.body : {status: 'ZUOU'}));
  }

}

