export interface IEntity {
  id?: number;
  versao?: number;
}

export class Entity implements IEntity {
  constructor(public id?: number, public versao?: number) {}
}
