import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOsoba } from 'app/shared/model/osoba.model';
import { OsobaService } from './osoba.service';

@Component({
  selector: 'jhi-osoba-delete-dialog',
  templateUrl: './osoba-delete-dialog.component.html'
})
export class OsobaDeleteDialogComponent {
  osoba: IOsoba;

  constructor(protected osobaService: OsobaService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.osobaService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'osobaListModification',
        content: 'Deleted an osoba'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-osoba-delete-popup',
  template: ''
})
export class OsobaDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ osoba }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(OsobaDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.osoba = osoba;
        this.ngbModalRef.result.then(
          () => {
            this.router.navigate(['/osoba', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          () => {
            this.router.navigate(['/osoba', { outlets: { popup: null } }]);
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
