import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITransakcja } from 'app/shared/model/transakcja.model';
import { TransakcjaService } from './transakcja.service';

@Component({
  selector: 'jhi-transakcja-delete-dialog',
  templateUrl: './transakcja-delete-dialog.component.html'
})
export class TransakcjaDeleteDialogComponent {
  transakcja: ITransakcja;

  constructor(
    protected transakcjaService: TransakcjaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.transakcjaService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'transakcjaListModification',
        content: 'Deleted an transakcja'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-transakcja-delete-popup',
  template: ''
})
export class TransakcjaDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ transakcja }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(TransakcjaDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.transakcja = transakcja;
        this.ngbModalRef.result.then(
          () => {
            this.router.navigate(['/transakcja', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          () => {
            this.router.navigate(['/transakcja', { outlets: { popup: null } }]);
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
