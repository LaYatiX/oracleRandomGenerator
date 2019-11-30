export interface IGodzinyOtwarcia {
  id?: number;
  poniedzialek?: string;
  wtorek?: string;
  sroda?: string;
  czwartek?: string;
  piatek?: string;
  sobota?: string;
  niedziela?: string;
}

export class GodzinyOtwarcia implements IGodzinyOtwarcia {
  constructor(
    public id?: number,
    public poniedzialek?: string,
    public wtorek?: string,
    public sroda?: string,
    public czwartek?: string,
    public piatek?: string,
    public sobota?: string,
    public niedziela?: string
  ) {}
}
