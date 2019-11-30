import { IProdukt } from 'app/shared/model/produkt.model';
import { ISklep } from 'app/shared/model/sklep.model';

export interface ITransakcja {
  id?: number;
  nettoo?: number;
  brutton?: number;
  vat?: number;
  produkties?: IProdukt[];
  sklep?: ISklep;
}

export class Transakcja implements ITransakcja {
  constructor(
    public id?: number,
    public nettoo?: number,
    public brutton?: number,
    public vat?: number,
    public produkties?: IProdukt[],
    public sklep?: ISklep
  ) {}
}
