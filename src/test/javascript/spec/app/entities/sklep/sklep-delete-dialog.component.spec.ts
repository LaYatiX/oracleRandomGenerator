import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { OracleTestModule } from '../../../test.module';
import { SklepDeleteDialogComponent } from 'app/entities/sklep/sklep-delete-dialog.component';
import { SklepService } from 'app/entities/sklep/sklep.service';

describe('Component Tests', () => {
  describe('Sklep Management Delete Component', () => {
    let comp: SklepDeleteDialogComponent;
    let fixture: ComponentFixture<SklepDeleteDialogComponent>;
    let service: SklepService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OracleTestModule],
        declarations: [SklepDeleteDialogComponent]
      })
        .overrideTemplate(SklepDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SklepDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SklepService);
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
