import { IGodzinyOtwarcia } from 'app/shared/model/godziny-otwarcia.model';
import { ITransakcja } from 'app/shared/model/transakcja.model';
import { IProdukt } from 'app/shared/model/produkt.model';
import { IOsoba } from 'app/shared/model/osoba.model';
import { TypSklepu } from 'app/shared/model/enumerations/typ-sklepu.model';

export interface ISklep {
  id?: number;
  typ?: TypSklepu;
  godzinyOtwarcia?: IGodzinyOtwarcia;
  transakcjes?: ITransakcja[];
  produkties?: IProdukt[];
  wlascicieles?: IOsoba[];
}

export class Sklep implements ISklep {
  constructor(
    public id?: number,
    public typ?: TypSklepu,
    public godzinyOtwarcia?: IGodzinyOtwarcia,
    public transakcjes?: ITransakcja[],
    public produkties?: IProdukt[],
    public wlascicieles?: IOsoba[]
  ) {}
}
