import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { OracleTestModule } from '../../../test.module';
import { TransakcjaDeleteDialogComponent } from 'app/entities/transakcja/transakcja-delete-dialog.component';
import { TransakcjaService } from 'app/entities/transakcja/transakcja.service';

describe('Component Tests', () => {
  describe('Transakcja Management Delete Component', () => {
    let comp: TransakcjaDeleteDialogComponent;
    let fixture: ComponentFixture<TransakcjaDeleteDialogComponent>;
    let service: TransakcjaService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OracleTestModule],
        declarations: [TransakcjaDeleteDialogComponent]
      })
        .overrideTemplate(TransakcjaDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TransakcjaDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TransakcjaService);
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
