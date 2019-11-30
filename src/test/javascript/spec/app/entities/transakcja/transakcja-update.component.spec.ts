import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { OracleTestModule } from '../../../test.module';
import { TransakcjaUpdateComponent } from 'app/entities/transakcja/transakcja-update.component';
import { TransakcjaService } from 'app/entities/transakcja/transakcja.service';
import { Transakcja } from 'app/shared/model/transakcja.model';

describe('Component Tests', () => {
  describe('Transakcja Management Update Component', () => {
    let comp: TransakcjaUpdateComponent;
    let fixture: ComponentFixture<TransakcjaUpdateComponent>;
    let service: TransakcjaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OracleTestModule],
        declarations: [TransakcjaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TransakcjaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TransakcjaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TransakcjaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Transakcja(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Transakcja();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
