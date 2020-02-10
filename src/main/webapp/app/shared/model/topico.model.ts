import { ITopico } from '../../shared/model/topico.model';
import { IGrupo } from '../../shared/model/grupo.interface';

export interface ITopico {
  id?: number;
  versao?: number;
  nome?: string;
  substituidos?: ITopico[];
  autorId?: number;
  substitutoId?: number;
  grupos?: IGrupo[];
}

export class Topico implements ITopico {
  constructor(
    public id?: number,
    public versao?: number,
    public nome?: string,
    public substituidos?: ITopico[],
    public autorId?: number,
    public substitutoId?: number,
    public grupos?: IGrupo[]
  ) {}
}
