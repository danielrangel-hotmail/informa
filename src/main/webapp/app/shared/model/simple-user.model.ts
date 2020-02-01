import { ISimpleUser } from 'app/shared/model/simples-user.interface';

export class SimpleUser implements ISimpleUser {
  constructor(
    public id?: any,
    public login?: string,
    public firstName?: string,
    public lastName?: string,
    public email?: string,
    public name?: string,
  ) {}
}
