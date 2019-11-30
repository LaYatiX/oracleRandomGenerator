import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { OracleTestModule } from '../../../test.module';
import { ProduktDeleteDialogComponent } from 'app/entities/produkt/produkt-delete-dialog.component';
import { ProduktService } from 'app/entities/produkt/produkt.service';

describe('Component Tests', () => {
  describe('Produkt Management Delete Component', () => {
    let comp: ProduktDeleteDialogComponent;
    let fixture: ComponentFixture<ProduktDeleteDialogComponent>;
    let service: ProduktService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OracleTestModule],
        declarations: [ProduktDeleteDialogComponent]
      })
        .overrideTemplate(ProduktDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProduktDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProduktService);
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
