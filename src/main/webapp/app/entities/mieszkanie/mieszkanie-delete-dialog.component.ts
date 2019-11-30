import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMieszkanie } from 'app/shared/model/mieszkanie.model';
import { MieszkanieService } from './mieszkanie.service';

@Component({
  selector: 'jhi-mieszkanie-delete-dialog',
  templateUrl: './mieszkanie-delete-dialog.component.html'
})
export class MieszkanieDeleteDialogComponent {
  mieszkanie: IMieszkanie;

  constructor(
    protected mieszkanieService: MieszkanieService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.mieszkanieService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'mieszkanieListModification',
        content: 'Deleted an mieszkanie'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-mieszkanie-delete-popup',
  template: ''
})
export class MieszkanieDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ mieszkanie }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(MieszkanieDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.mieszkanie = mieszkanie;
        this.ngbModalRef.result.then(
          () => {
            this.router.navigate(['/mieszkanie', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          () => {
            this.router.navigate(['/mieszkanie', { outlets: { popup: null } }]);
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
