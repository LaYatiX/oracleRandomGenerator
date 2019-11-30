import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { OracleTestModule } from '../../../test.module';
import { SklepUpdateComponent } from 'app/entities/sklep/sklep-update.component';
import { SklepService } from 'app/entities/sklep/sklep.service';
import { Sklep } from 'app/shared/model/sklep.model';

describe('Component Tests', () => {
  describe('Sklep Management Update Component', () => {
    let comp: SklepUpdateComponent;
    let fixture: ComponentFixture<SklepUpdateComponent>;
    let service: SklepService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OracleTestModule],
        declarations: [SklepUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SklepUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SklepUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SklepService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Sklep(123);
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
        const entity = new Sklep();
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
