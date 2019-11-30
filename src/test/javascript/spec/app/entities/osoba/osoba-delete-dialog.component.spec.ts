import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { OracleTestModule } from '../../../test.module';
import { OsobaDeleteDialogComponent } from 'app/entities/osoba/osoba-delete-dialog.component';
import { OsobaService } from 'app/entities/osoba/osoba.service';

describe('Component Tests', () => {
  describe('Osoba Management Delete Component', () => {
    let comp: OsobaDeleteDialogComponent;
    let fixture: ComponentFixture<OsobaDeleteDialogComponent>;
    let service: OsobaService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OracleTestModule],
        declarations: [OsobaDeleteDialogComponent]
      })
        .overrideTemplate(OsobaDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OsobaDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OsobaService);
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
