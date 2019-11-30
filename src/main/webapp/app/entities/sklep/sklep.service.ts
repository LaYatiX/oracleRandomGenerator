import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISklep } from 'app/shared/model/sklep.model';

type EntityResponseType = HttpResponse<ISklep>;
type EntityArrayResponseType = HttpResponse<ISklep[]>;

@Injectable({ providedIn: 'root' })
export class SklepService {
  public resourceUrl = SERVER_API_URL + 'api/skleps';

  constructor(protected http: HttpClient) {}

  create(sklep: ISklep): Observable<EntityResponseType> {
    return this.http.post<ISklep>(this.resourceUrl, sklep, { observe: 'response' });
  }

  update(sklep: ISklep): Observable<EntityResponseType> {
    return this.http.put<ISklep>(this.resourceUrl, sklep, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISklep>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISklep[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
