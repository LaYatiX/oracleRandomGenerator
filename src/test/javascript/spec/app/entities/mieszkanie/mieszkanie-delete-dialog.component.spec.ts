import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { OracleTestModule } from '../../../test.module';
import { MieszkanieDeleteDialogComponent } from 'app/entities/mieszkanie/mieszkanie-delete-dialog.component';
import { MieszkanieService } from 'app/entities/mieszkanie/mieszkanie.service';

describe('Component Tests', () => {
  describe('Mieszkanie Management Delete Component', () => {
    let comp: MieszkanieDeleteDialogComponent;
    let fixture: ComponentFixture<MieszkanieDeleteDialogComponent>;
    let service: MieszkanieService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OracleTestModule],
        declarations: [MieszkanieDeleteDialogComponent]
      })
        .overrideTemplate(MieszkanieDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MieszkanieDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MieszkanieService);
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
