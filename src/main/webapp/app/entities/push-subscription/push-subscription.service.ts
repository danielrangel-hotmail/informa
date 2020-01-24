import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPushSubscription } from 'app/shared/model/push-subscription.model';

type EntityResponseType = HttpResponse<IPushSubscription>;
type EntityArrayResponseType = HttpResponse<IPushSubscription[]>;

@Injectable({ providedIn: 'root' })
export class PushSubscriptionService {
  public resourceUrl = SERVER_API_URL + 'api/push-subscriptions';

  constructor(protected http: HttpClient) {}

  create(pushSubscription: IPushSubscription): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pushSubscription);
    return this.http
      .post<IPushSubscription>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(pushSubscription: IPushSubscription): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pushSubscription);
    return this.http
      .put<IPushSubscription>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPushSubscription>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPushSubscription[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(pushSubscription: IPushSubscription): IPushSubscription {
    const copy: IPushSubscription = Object.assign({}, pushSubscription, {
      criacao: pushSubscription.criacao && pushSubscription.criacao.isValid() ? pushSubscription.criacao.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.criacao = res.body.criacao ? moment(res.body.criacao) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((pushSubscription: IPushSubscription) => {
        pushSubscription.criacao = pushSubscription.criacao ? moment(pushSubscription.criacao) : undefined;
      });
    }
    return res;
  }

  private convertKeyToString(key: ArrayBuffer | null): string {
    if (key == null) return "";
    return btoa(String.fromCharCode.apply(null, Array.from(new Uint8Array(key))));
  }


  addPushSubscriber(sub: PushSubscription): Observable<EntityResponseType> {
    const auth = this.convertKeyToString(sub.getKey("auth"));
    const p256dh = this.convertKeyToString(sub.getKey("p256dh"));
    const pushSubscription: IPushSubscription = {
      endpoint: sub.endpoint,
      key: p256dh,
      auth
    };
    return this.create(pushSubscription);
  }
}
