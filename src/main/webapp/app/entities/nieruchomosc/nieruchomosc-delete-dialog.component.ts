import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { INieruchomosc } from 'app/shared/model/nieruchomosc.model';
import { NieruchomoscService } from './nieruchomosc.service';

@Component({
  selector: 'jhi-nieruchomosc-delete-dialog',
  templateUrl: './nieruchomosc-delete-dialog.component.html'
})
export class NieruchomoscDeleteDialogComponent {
  nieruchomosc: INieruchomosc;

  constructor(
    protected nieruchomoscService: NieruchomoscService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.nieruchomoscService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'nieruchomoscListModification',
        content: 'Deleted an nieruchomosc'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-nieruchomosc-delete-popup',
  template: ''
})
export class NieruchomoscDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ nieruchomosc }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(NieruchomoscDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.nieruchomosc = nieruchomosc;
        this.ngbModalRef.result.then(
          () => {
            this.router.navigate(['/nieruchomosc', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          () => {
            this.router.navigate(['/nieruchomosc', { outlets: { popup: null } }]);
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
