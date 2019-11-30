import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAdres } from 'app/shared/model/adres.model';
import { AdresService } from './adres.service';

@Component({
  selector: 'jhi-adres-delete-dialog',
  templateUrl: './adres-delete-dialog.component.html'
})
export class AdresDeleteDialogComponent {
  adres: IAdres;

  constructor(protected adresService: AdresService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.adresService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'adresListModification',
        content: 'Deleted an adres'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-adres-delete-popup',
  template: ''
})
export class AdresDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ adres }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(AdresDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.adres = adres;
        this.ngbModalRef.result.then(
          () => {
            this.router.navigate(['/adres', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          () => {
            this.router.navigate(['/adres', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
