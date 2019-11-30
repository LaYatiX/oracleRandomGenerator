import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Nieruchomosc } from 'app/shared/model/nieruchomosc.model';
import { NieruchomoscService } from './nieruchomosc.service';
import { NieruchomoscComponent } from './nieruchomosc.component';
import { NieruchomoscDetailComponent } from './nieruchomosc-detail.component';
import { NieruchomoscUpdateComponent } from './nieruchomosc-update.component';
import { NieruchomoscDeletePopupComponent } from './nieruchomosc-delete-dialog.component';
import { INieruchomosc } from 'app/shared/model/nieruchomosc.model';

@Injectable({ providedIn: 'root' })
export class NieruchomoscResolve implements Resolve<INieruchomosc> {
  constructor(private service: NieruchomoscService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<INieruchomosc> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((nieruchomosc: HttpResponse<Nieruchomosc>) => nieruchomosc.body));
    }
    return of(new Nieruchomosc());
  }
}

export const nieruchomoscRoute: Routes = [
  {
    path: '',
    component: NieruchomoscComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Nieruchomoscs'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: NieruchomoscDetailComponent,
    resolve: {
      nieruchomosc: NieruchomoscResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Nieruchomoscs'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: NieruchomoscUpdateComponent,
    resolve: {
      nieruchomosc: NieruchomoscResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Nieruchomoscs'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: NieruchomoscUpdateComponent,
    resolve: {
      nieruchomosc: NieruchomoscResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Nieruchomoscs'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const nieruchomoscPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: NieruchomoscDeletePopupComponent,
    resolve: {
      nieruchomosc: NieruchomoscResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Nieruchomoscs'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
