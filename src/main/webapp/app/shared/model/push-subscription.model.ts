import { Moment } from 'moment';

export interface IPushSubscription {
  id?: number;
  versao?: number;
  criacao?: Moment;
  endpoint?: string;
  key?: string;
  auth?: string;
  perfilId?: number;
}

export class PushSubscription implements IPushSubscription {
  constructor(
    public id?: number,
    public versao?: number,
    public criacao?: Moment,
    public endpoint?: string,
    public key?: string,
    public auth?: string,
    public perfilId?: number
  ) {}
}
