import { ITransakcja } from 'app/shared/model/transakcja.model';
import { ISklep } from 'app/shared/model/sklep.model';

export interface IProdukt {
  id?: number;
  nazwa?: string;
  wartosc?: number;
  vat?: number;
  transakcja?: ITransakcja;
  sklep?: ISklep;
}

export class Produkt implements IProdukt {
  constructor(
    public id?: number,
    public nazwa?: string,
    public wartosc?: number,
    public vat?: number,
    public transakcja?: ITransakcja,
    public sklep?: ISklep
  ) {}
}
