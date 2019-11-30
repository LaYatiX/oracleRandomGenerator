import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ILokalizacja } from 'app/shared/model/lokalizacja.model';

type EntityResponseType = HttpResponse<ILokalizacja>;
type EntityArrayResponseType = HttpResponse<ILokalizacja[]>;

@Injectable({ providedIn: 'root' })
export class LokalizacjaService {
  public resourceUrl = SERVER_API_URL + 'api/lokalizacjas';

  constructor(protected http: HttpClient) {}

  create(lokalizacja: ILokalizacja): Observable<EntityResponseType> {
    return this.http.post<ILokalizacja>(this.resourceUrl, lokalizacja, { observe: 'response' });
  }

  update(lokalizacja: ILokalizacja): Observable<EntityResponseType> {
    return this.http.put<ILokalizacja>(this.resourceUrl, lokalizacja, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ILokalizacja>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ILokalizacja[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
