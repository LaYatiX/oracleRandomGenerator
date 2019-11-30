import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { GodzinyOtwarcia } from 'app/shared/model/godziny-otwarcia.model';
import { GodzinyOtwarciaService } from './godziny-otwarcia.service';
import { GodzinyOtwarciaComponent } from './godziny-otwarcia.component';
import { GodzinyOtwarciaDetailComponent } from './godziny-otwarcia-detail.component';
import { GodzinyOtwarciaUpdateComponent } from './godziny-otwarcia-update.component';
import { GodzinyOtwarciaDeletePopupComponent } from './godziny-otwarcia-delete-dialog.component';
import { IGodzinyOtwarcia } from 'app/shared/model/godziny-otwarcia.model';

@Injectable({ providedIn: 'root' })
export class GodzinyOtwarciaResolve implements Resolve<IGodzinyOtwarcia> {
  constructor(private service: GodzinyOtwarciaService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IGodzinyOtwarcia> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((godzinyOtwarcia: HttpResponse<GodzinyOtwarcia>) => godzinyOtwarcia.body));
    }
    return of(new GodzinyOtwarcia());
  }
}

export const godzinyOtwarciaRoute: Routes = [
  {
    path: '',
    component: GodzinyOtwarciaComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'GodzinyOtwarcias'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: GodzinyOtwarciaDetailComponent,
    resolve: {
      godzinyOtwarcia: GodzinyOtwarciaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'GodzinyOtwarcias'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: GodzinyOtwarciaUpdateComponent,
    resolve: {
      godzinyOtwarcia: GodzinyOtwarciaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'GodzinyOtwarcias'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: GodzinyOtwarciaUpdateComponent,
    resolve: {
      godzinyOtwarcia: GodzinyOtwarciaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'GodzinyOtwarcias'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const godzinyOtwarciaPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: GodzinyOtwarciaDeletePopupComponent,
    resolve: {
      godzinyOtwarcia: GodzinyOtwarciaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'GodzinyOtwarcias'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
