import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Sklep } from 'app/shared/model/sklep.model';
import { SklepService } from './sklep.service';
import { SklepComponent } from './sklep.component';
import { SklepDetailComponent } from './sklep-detail.component';
import { SklepUpdateComponent } from './sklep-update.component';
import { SklepDeletePopupComponent } from './sklep-delete-dialog.component';
import { ISklep } from 'app/shared/model/sklep.model';

@Injectable({ providedIn: 'root' })
export class SklepResolve implements Resolve<ISklep> {
  constructor(private service: SklepService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISklep> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((sklep: HttpResponse<Sklep>) => sklep.body));
    }
    return of(new Sklep());
  }
}

export const sklepRoute: Routes = [
  {
    path: '',
    component: SklepComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Skleps'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SklepDetailComponent,
    resolve: {
      sklep: SklepResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Skleps'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SklepUpdateComponent,
    resolve: {
      sklep: SklepResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Skleps'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SklepUpdateComponent,
    resolve: {
      sklep: SklepResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Skleps'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const sklepPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: SklepDeletePopupComponent,
    resolve: {
      sklep: SklepResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Skleps'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
