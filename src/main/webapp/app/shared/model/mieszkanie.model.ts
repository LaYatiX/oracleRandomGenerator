import { INieruchomosc } from 'app/shared/model/nieruchomosc.model';

export interface IMieszkanie {
  id?: number;
  metraz?: number;
  czyLazienka?: boolean;
  czyKuchnia?: boolean;
  czyWyposazone?: boolean;
  nieruchomosc?: INieruchomosc;
}

export class Mieszkanie implements IMieszkanie {
  constructor(
    public id?: number,
    public metraz?: number,
    public czyLazienka?: boolean,
    public czyKuchnia?: boolean,
    public czyWyposazone?: boolean,
    public nieruchomosc?: INieruchomosc
  ) {
    this.czyLazienka = this.czyLazienka || false;
    this.czyKuchnia = this.czyKuchnia || false;
    this.czyWyposazone = this.czyWyposazone || false;
  }
}
