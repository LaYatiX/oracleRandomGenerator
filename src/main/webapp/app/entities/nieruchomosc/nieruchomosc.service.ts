import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { INieruchomosc } from 'app/shared/model/nieruchomosc.model';

type EntityResponseType = HttpResponse<INieruchomosc>;
type EntityArrayResponseType = HttpResponse<INieruchomosc[]>;

@Injectable({ providedIn: 'root' })
export class NieruchomoscService {
  public resourceUrl = SERVER_API_URL + 'api/nieruchomoscs';

  constructor(protected http: HttpClient) {}

  create(nieruchomosc: INieruchomosc): Observable<EntityResponseType> {
    return this.http.post<INieruchomosc>(this.resourceUrl, nieruchomosc, { observe: 'response' });
  }

  update(nieruchomosc: INieruchomosc): Observable<EntityResponseType> {
    return this.http.put<INieruchomosc>(this.resourceUrl, nieruchomosc, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<INieruchomosc>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<INieruchomosc[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
