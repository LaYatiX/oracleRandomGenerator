import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { OracleTestModule } from '../../../test.module';
import { NieruchomoscDeleteDialogComponent } from 'app/entities/nieruchomosc/nieruchomosc-delete-dialog.component';
import { NieruchomoscService } from 'app/entities/nieruchomosc/nieruchomosc.service';

describe('Component Tests', () => {
  describe('Nieruchomosc Management Delete Component', () => {
    let comp: NieruchomoscDeleteDialogComponent;
    let fixture: ComponentFixture<NieruchomoscDeleteDialogComponent>;
    let service: NieruchomoscService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OracleTestModule],
        declarations: [NieruchomoscDeleteDialogComponent]
      })
        .overrideTemplate(NieruchomoscDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(NieruchomoscDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(NieruchomoscService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
