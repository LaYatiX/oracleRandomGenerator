import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Lokalizacja } from 'app/shared/model/lokalizacja.model';
import { LokalizacjaService } from './lokalizacja.service';
import { LokalizacjaComponent } from './lokalizacja.component';
import { LokalizacjaDetailComponent } from './lokalizacja-detail.component';
import { LokalizacjaUpdateComponent } from './lokalizacja-update.component';
import { LokalizacjaDeletePopupComponent } from './lokalizacja-delete-dialog.component';
import { ILokalizacja } from 'app/shared/model/lokalizacja.model';

@Injectable({ providedIn: 'root' })
export class LokalizacjaResolve implements Resolve<ILokalizacja> {
  constructor(private service: LokalizacjaService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILokalizacja> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((lokalizacja: HttpResponse<Lokalizacja>) => lokalizacja.body));
    }
    return of(new Lokalizacja());
  }
}

export const lokalizacjaRoute: Routes = [
  {
    path: '',
    component: LokalizacjaComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Lokalizacjas'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: LokalizacjaDetailComponent,
    resolve: {
      lokalizacja: LokalizacjaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Lokalizacjas'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: LokalizacjaUpdateComponent,
    resolve: {
      lokalizacja: LokalizacjaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Lokalizacjas'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: LokalizacjaUpdateComponent,
    resolve: {
      lokalizacja: LokalizacjaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Lokalizacjas'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const lokalizacjaPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: LokalizacjaDeletePopupComponent,
    resolve: {
      lokalizacja: LokalizacjaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Lokalizacjas'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
