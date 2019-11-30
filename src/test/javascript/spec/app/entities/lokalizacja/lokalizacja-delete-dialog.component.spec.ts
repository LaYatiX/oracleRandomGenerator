import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { OracleTestModule } from '../../../test.module';
import { LokalizacjaDeleteDialogComponent } from 'app/entities/lokalizacja/lokalizacja-delete-dialog.component';
import { LokalizacjaService } from 'app/entities/lokalizacja/lokalizacja.service';

describe('Component Tests', () => {
  describe('Lokalizacja Management Delete Component', () => {
    let comp: LokalizacjaDeleteDialogComponent;
    let fixture: ComponentFixture<LokalizacjaDeleteDialogComponent>;
    let service: LokalizacjaService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OracleTestModule],
        declarations: [LokalizacjaDeleteDialogComponent]
      })
        .overrideTemplate(LokalizacjaDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LokalizacjaDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LokalizacjaService);
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
