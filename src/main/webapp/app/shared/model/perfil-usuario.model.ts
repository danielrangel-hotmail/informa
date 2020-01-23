import { Moment } from 'moment';
import { IPushSubscription } from 'app/shared/model/push-subscription.model';

export interface IPerfilUsuario {
  id?: number;
  criacao?: Moment;
  ultimaEdicao?: Moment;
  versao?: number;
  entradaNaEmpresa?: Moment;
  saidaDaEmpresa?: Moment;
  nascimento?: Moment;
  skype?: string;
  usuarioId?: number;
  subscriptions?: IPushSubscription[];
}

export class PerfilUsuario implements IPerfilUsuario {
  constructor(
    public id?: number,
    public criacao?: Moment,
    public ultimaEdicao?: Moment,
    public versao?: number,
    public entradaNaEmpresa?: Moment,
    public saidaDaEmpresa?: Moment,
    public nascimento?: Moment,
    public skype?: string,
    public usuarioId?: number,
    public subscriptions?: IPushSubscription[]
  ) {}
}
