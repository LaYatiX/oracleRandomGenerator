import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Mieszkanie } from 'app/shared/model/mieszkanie.model';
import { MieszkanieService } from './mieszkanie.service';
import { MieszkanieComponent } from './mieszkanie.component';
import { MieszkanieDetailComponent } from './mieszkanie-detail.component';
import { MieszkanieUpdateComponent } from './mieszkanie-update.component';
import { MieszkanieDeletePopupComponent } from './mieszkanie-delete-dialog.component';
import { IMieszkanie } from 'app/shared/model/mieszkanie.model';

@Injectable({ providedIn: 'root' })
export class MieszkanieResolve implements Resolve<IMieszkanie> {
  constructor(private service: MieszkanieService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMieszkanie> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((mieszkanie: HttpResponse<Mieszkanie>) => mieszkanie.body));
    }
    return of(new Mieszkanie());
  }
}

export const mieszkanieRoute: Routes = [
  {
    path: '',
    component: MieszkanieComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Mieszkanies'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: MieszkanieDetailComponent,
    resolve: {
      mieszkanie: MieszkanieResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Mieszkanies'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: MieszkanieUpdateComponent,
    resolve: {
      mieszkanie: MieszkanieResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Mieszkanies'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: MieszkanieUpdateComponent,
    resolve: {
      mieszkanie: MieszkanieResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Mieszkanies'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const mieszkaniePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: MieszkanieDeletePopupComponent,
    resolve: {
      mieszkanie: MieszkanieResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Mieszkanies'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
