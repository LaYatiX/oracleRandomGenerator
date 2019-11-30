import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IGodzinyOtwarcia } from 'app/shared/model/godziny-otwarcia.model';

type EntityResponseType = HttpResponse<IGodzinyOtwarcia>;
type EntityArrayResponseType = HttpResponse<IGodzinyOtwarcia[]>;

@Injectable({ providedIn: 'root' })
export class GodzinyOtwarciaService {
  public resourceUrl = SERVER_API_URL + 'api/godziny-otwarcias';

  constructor(protected http: HttpClient) {}

  create(godzinyOtwarcia: IGodzinyOtwarcia): Observable<EntityResponseType> {
    return this.http.post<IGodzinyOtwarcia>(this.resourceUrl, godzinyOtwarcia, { observe: 'response' });
  }

  update(godzinyOtwarcia: IGodzinyOtwarcia): Observable<EntityResponseType> {
    return this.http.put<IGodzinyOtwarcia>(this.resourceUrl, godzinyOtwarcia, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IGodzinyOtwarcia>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IGodzinyOtwarcia[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
