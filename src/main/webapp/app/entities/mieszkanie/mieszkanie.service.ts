import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IMieszkanie } from 'app/shared/model/mieszkanie.model';

type EntityResponseType = HttpResponse<IMieszkanie>;
type EntityArrayResponseType = HttpResponse<IMieszkanie[]>;

@Injectable({ providedIn: 'root' })
export class MieszkanieService {
  public resourceUrl = SERVER_API_URL + 'api/mieszkanies';

  constructor(protected http: HttpClient) {}

  create(mieszkanie: IMieszkanie): Observable<EntityResponseType> {
    return this.http.post<IMieszkanie>(this.resourceUrl, mieszkanie, { observe: 'response' });
  }

  update(mieszkanie: IMieszkanie): Observable<EntityResponseType> {
    return this.http.put<IMieszkanie>(this.resourceUrl, mieszkanie, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMieszkanie>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMieszkanie[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
