import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILokalizacja } from 'app/shared/model/lokalizacja.model';
import { LokalizacjaService } from './lokalizacja.service';

@Component({
  selector: 'jhi-lokalizacja-delete-dialog',
  templateUrl: './lokalizacja-delete-dialog.component.html'
})
export class LokalizacjaDeleteDialogComponent {
  lokalizacja: ILokalizacja;

  constructor(
    protected lokalizacjaService: LokalizacjaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.lokalizacjaService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'lokalizacjaListModification',
        content: 'Deleted an lokalizacja'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-lokalizacja-delete-popup',
  template: ''
})
export class LokalizacjaDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ lokalizacja }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(LokalizacjaDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.lokalizacja = lokalizacja;
        this.ngbModalRef.result.then(
          () => {
            this.router.navigate(['/lokalizacja', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          () => {
            this.router.navigate(['/lokalizacja', { outlets: { popup: null } }]);
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
