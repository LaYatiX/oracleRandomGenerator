import { IAdres } from 'app/shared/model/adres.model';
import { IMieszkanie } from 'app/shared/model/mieszkanie.model';
import { TypNieruchomosci } from 'app/shared/model/enumerations/typ-nieruchomosci.model';

export interface INieruchomosc {
  id?: number;
  typ?: TypNieruchomosci;
  iloscMieszkan?: number;
  iloscMieszkancow?: number;
  adres?: IAdres;
  mieszkanias?: IMieszkanie[];
}

export class Nieruchomosc implements INieruchomosc {
  constructor(
    public id?: number,
    public typ?: TypNieruchomosci,
    public iloscMieszkan?: number,
    public iloscMieszkancow?: number,
    public adres?: IAdres,
    public mieszkanias?: IMieszkanie[]
  ) {}
}
