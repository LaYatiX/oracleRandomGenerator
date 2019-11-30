import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Adres } from 'app/shared/model/adres.model';
import { AdresService } from './adres.service';
import { AdresComponent } from './adres.component';
import { AdresDetailComponent } from './adres-detail.component';
import { AdresUpdateComponent } from './adres-update.component';
import { AdresDeletePopupComponent } from './adres-delete-dialog.component';
import { IAdres } from 'app/shared/model/adres.model';

@Injectable({ providedIn: 'root' })
export class AdresResolve implements Resolve<IAdres> {
  constructor(private service: AdresService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAdres> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((adres: HttpResponse<Adres>) => adres.body));
    }
    return of(new Adres());
  }
}

export const adresRoute: Routes = [
  {
    path: '',
    component: AdresComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Adres'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AdresDetailComponent,
    resolve: {
      adres: AdresResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Adres'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AdresUpdateComponent,
    resolve: {
      adres: AdresResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Adres'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AdresUpdateComponent,
    resolve: {
      adres: AdresResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Adres'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const adresPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: AdresDeletePopupComponent,
    resolve: {
      adres: AdresResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Adres'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
