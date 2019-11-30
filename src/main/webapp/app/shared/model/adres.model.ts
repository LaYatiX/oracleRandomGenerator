import { ILokalizacja } from 'app/shared/model/lokalizacja.model';

export interface IAdres {
  id?: number;
  miasto?: string;
  ulica?: string;
  nrDomu?: string;
  nrLokalu?: string;
  wojewodzwtwo?: string;
  powiat?: string;
  gmina?: string;
  kodPocztowy?: string;
  kraj?: string;
  lokalizacja?: ILokalizacja;
}

export class Adres implements IAdres {
  constructor(
    public id?: number,
    public miasto?: string,
    public ulica?: string,
    public nrDomu?: string,
    public nrLokalu?: string,
    public wojewodzwtwo?: string,
    public powiat?: string,
    public gmina?: string,
    public kodPocztowy?: string,
    public kraj?: string,
    public lokalizacja?: ILokalizacja
  ) {}
}
