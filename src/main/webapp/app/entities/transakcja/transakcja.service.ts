import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITransakcja } from 'app/shared/model/transakcja.model';

type EntityResponseType = HttpResponse<ITransakcja>;
type EntityArrayResponseType = HttpResponse<ITransakcja[]>;

@Injectable({ providedIn: 'root' })
export class TransakcjaService {
  public resourceUrl = SERVER_API_URL + 'api/transakcjas';

  constructor(protected http: HttpClient) {}

  create(transakcja: ITransakcja): Observable<EntityResponseType> {
    return this.http.post<ITransakcja>(this.resourceUrl, transakcja, { observe: 'response' });
  }

  update(transakcja: ITransakcja): Observable<EntityResponseType> {
    return this.http.put<ITransakcja>(this.resourceUrl, transakcja, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITransakcja>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITransakcja[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
