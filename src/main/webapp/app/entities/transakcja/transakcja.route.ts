import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Transakcja } from 'app/shared/model/transakcja.model';
import { TransakcjaService } from './transakcja.service';
import { TransakcjaComponent } from './transakcja.component';
import { TransakcjaDetailComponent } from './transakcja-detail.component';
import { TransakcjaUpdateComponent } from './transakcja-update.component';
import { TransakcjaDeletePopupComponent } from './transakcja-delete-dialog.component';
import { ITransakcja } from 'app/shared/model/transakcja.model';

@Injectable({ providedIn: 'root' })
export class TransakcjaResolve implements Resolve<ITransakcja> {
  constructor(private service: TransakcjaService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITransakcja> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((transakcja: HttpResponse<Transakcja>) => transakcja.body));
    }
    return of(new Transakcja());
  }
}

export const transakcjaRoute: Routes = [
  {
    path: '',
    component: TransakcjaComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Transakcjas'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TransakcjaDetailComponent,
    resolve: {
      transakcja: TransakcjaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Transakcjas'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TransakcjaUpdateComponent,
    resolve: {
      transakcja: TransakcjaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Transakcjas'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TransakcjaUpdateComponent,
    resolve: {
      transakcja: TransakcjaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Transakcjas'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const transakcjaPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: TransakcjaDeletePopupComponent,
    resolve: {
      transakcja: TransakcjaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Transakcjas'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
