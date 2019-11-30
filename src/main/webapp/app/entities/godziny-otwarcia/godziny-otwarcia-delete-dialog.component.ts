import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IGodzinyOtwarcia } from 'app/shared/model/godziny-otwarcia.model';
import { GodzinyOtwarciaService } from './godziny-otwarcia.service';

@Component({
  selector: 'jhi-godziny-otwarcia-delete-dialog',
  templateUrl: './godziny-otwarcia-delete-dialog.component.html'
})
export class GodzinyOtwarciaDeleteDialogComponent {
  godzinyOtwarcia: IGodzinyOtwarcia;

  constructor(
    protected godzinyOtwarciaService: GodzinyOtwarciaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.godzinyOtwarciaService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'godzinyOtwarciaListModification',
        content: 'Deleted an godzinyOtwarcia'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-godziny-otwarcia-delete-popup',
  template: ''
})
export class GodzinyOtwarciaDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ godzinyOtwarcia }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(GodzinyOtwarciaDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.godzinyOtwarcia = godzinyOtwarcia;
        this.ngbModalRef.result.then(
          () => {
            this.router.navigate(['/godziny-otwarcia', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          () => {
            this.router.navigate(['/godziny-otwarcia', { outlets: { popup: null } }]);
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
