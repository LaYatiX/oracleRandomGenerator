import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProdukt } from 'app/shared/model/produkt.model';
import { ProduktService } from './produkt.service';

@Component({
  selector: 'jhi-produkt-delete-dialog',
  templateUrl: './produkt-delete-dialog.component.html'
})
export class ProduktDeleteDialogComponent {
  produkt: IProdukt;

  constructor(protected produktService: ProduktService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.produktService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'produktListModification',
        content: 'Deleted an produkt'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-produkt-delete-popup',
  template: ''
})
export class ProduktDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ produkt }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ProduktDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.produkt = produkt;
        this.ngbModalRef.result.then(
          () => {
            this.router.navigate(['/produkt', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          () => {
            this.router.navigate(['/produkt', { outlets: { popup: null } }]);
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
