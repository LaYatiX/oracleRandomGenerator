import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISklep } from 'app/shared/model/sklep.model';
import { SklepService } from './sklep.service';

@Component({
  selector: 'jhi-sklep-delete-dialog',
  templateUrl: './sklep-delete-dialog.component.html'
})
export class SklepDeleteDialogComponent {
  sklep: ISklep;

  constructor(protected sklepService: SklepService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.sklepService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'sklepListModification',
        content: 'Deleted an sklep'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-sklep-delete-popup',
  template: ''
})
export class SklepDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ sklep }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(SklepDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.sklep = sklep;
        this.ngbModalRef.result.then(
          () => {
            this.router.navigate(['/sklep', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          () => {
            this.router.navigate(['/sklep', { outlets: { popup: null } }]);
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
