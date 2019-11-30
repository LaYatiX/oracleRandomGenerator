import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Produkt } from 'app/shared/model/produkt.model';
import { ProduktService } from './produkt.service';
import { ProduktComponent } from './produkt.component';
import { ProduktDetailComponent } from './produkt-detail.component';
import { ProduktUpdateComponent } from './produkt-update.component';
import { ProduktDeletePopupComponent } from './produkt-delete-dialog.component';
import { IProdukt } from 'app/shared/model/produkt.model';

@Injectable({ providedIn: 'root' })
export class ProduktResolve implements Resolve<IProdukt> {
  constructor(private service: ProduktService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProdukt> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((produkt: HttpResponse<Produkt>) => produkt.body));
    }
    return of(new Produkt());
  }
}

export const produktRoute: Routes = [
  {
    path: '',
    component: ProduktComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Produkts'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ProduktDetailComponent,
    resolve: {
      produkt: ProduktResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Produkts'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ProduktUpdateComponent,
    resolve: {
      produkt: ProduktResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Produkts'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ProduktUpdateComponent,
    resolve: {
      produkt: ProduktResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Produkts'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const produktPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ProduktDeletePopupComponent,
    resolve: {
      produkt: ProduktResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Produkts'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
