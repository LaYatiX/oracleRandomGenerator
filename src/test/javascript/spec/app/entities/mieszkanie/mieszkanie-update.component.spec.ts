import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { OracleTestModule } from '../../../test.module';
import { MieszkanieUpdateComponent } from 'app/entities/mieszkanie/mieszkanie-update.component';
import { MieszkanieService } from 'app/entities/mieszkanie/mieszkanie.service';
import { Mieszkanie } from 'app/shared/model/mieszkanie.model';

describe('Component Tests', () => {
  describe('Mieszkanie Management Update Component', () => {
    let comp: MieszkanieUpdateComponent;
    let fixture: ComponentFixture<MieszkanieUpdateComponent>;
    let service: MieszkanieService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OracleTestModule],
        declarations: [MieszkanieUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(MieszkanieUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MieszkanieUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MieszkanieService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Mieszkanie(123);
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
        const entity = new Mieszkanie();
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
