import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAdres } from 'app/shared/model/adres.model';

type EntityResponseType = HttpResponse<IAdres>;
type EntityArrayResponseType = HttpResponse<IAdres[]>;

@Injectable({ providedIn: 'root' })
export class AdresService {
  public resourceUrl = SERVER_API_URL + 'api/adres';

  constructor(protected http: HttpClient) {}

  create(adres: IAdres): Observable<EntityResponseType> {
    return this.http.post<IAdres>(this.resourceUrl, adres, { observe: 'response' });
  }

  update(adres: IAdres): Observable<EntityResponseType> {
    return this.http.put<IAdres>(this.resourceUrl, adres, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAdres>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAdres[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
