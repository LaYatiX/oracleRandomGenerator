export interface ILokalizacja {
  id?: number;
  lat?: number;
  lng?: number;
}

export class Lokalizacja implements ILokalizacja {
  constructor(public id?: number, public lat?: number, public lng?: number) {}
}
