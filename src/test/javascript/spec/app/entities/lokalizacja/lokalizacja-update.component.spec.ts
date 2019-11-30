import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { OracleTestModule } from '../../../test.module';
import { LokalizacjaUpdateComponent } from 'app/entities/lokalizacja/lokalizacja-update.component';
import { LokalizacjaService } from 'app/entities/lokalizacja/lokalizacja.service';
import { Lokalizacja } from 'app/shared/model/lokalizacja.model';

describe('Component Tests', () => {
  describe('Lokalizacja Management Update Component', () => {
    let comp: LokalizacjaUpdateComponent;
    let fixture: ComponentFixture<LokalizacjaUpdateComponent>;
    let service: LokalizacjaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OracleTestModule],
        declarations: [LokalizacjaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(LokalizacjaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LokalizacjaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LokalizacjaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Lokalizacja(123);
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
        const entity = new Lokalizacja();
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
