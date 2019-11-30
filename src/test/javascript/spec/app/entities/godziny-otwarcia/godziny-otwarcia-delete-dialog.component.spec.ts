import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { OracleTestModule } from '../../../test.module';
import { GodzinyOtwarciaDeleteDialogComponent } from 'app/entities/godziny-otwarcia/godziny-otwarcia-delete-dialog.component';
import { GodzinyOtwarciaService } from 'app/entities/godziny-otwarcia/godziny-otwarcia.service';

describe('Component Tests', () => {
  describe('GodzinyOtwarcia Management Delete Component', () => {
    let comp: GodzinyOtwarciaDeleteDialogComponent;
    let fixture: ComponentFixture<GodzinyOtwarciaDeleteDialogComponent>;
    let service: GodzinyOtwarciaService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OracleTestModule],
        declarations: [GodzinyOtwarciaDeleteDialogComponent]
      })
        .overrideTemplate(GodzinyOtwarciaDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(GodzinyOtwarciaDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GodzinyOtwarciaService);
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
