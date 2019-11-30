import { IAdres } from 'app/shared/model/adres.model';
import { ISklep } from 'app/shared/model/sklep.model';

export interface IOsoba {
  id?: number;
  imie?: string;
  nazwisko?: string;
  telefon?: string;
  pesel?: string;
  adres?: IAdres;
  sklep?: ISklep;
}

export class Osoba implements IOsoba {
  constructor(
    public id?: number,
    public imie?: string,
    public nazwisko?: string,
    public telefon?: string,
    public pesel?: string,
    public adres?: IAdres,
    public sklep?: ISklep
  ) {}
}
