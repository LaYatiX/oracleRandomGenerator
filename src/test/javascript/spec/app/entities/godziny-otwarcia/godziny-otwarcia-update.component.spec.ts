import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { OracleTestModule } from '../../../test.module';
import { GodzinyOtwarciaUpdateComponent } from 'app/entities/godziny-otwarcia/godziny-otwarcia-update.component';
import { GodzinyOtwarciaService } from 'app/entities/godziny-otwarcia/godziny-otwarcia.service';
import { GodzinyOtwarcia } from 'app/shared/model/godziny-otwarcia.model';

describe('Component Tests', () => {
  describe('GodzinyOtwarcia Management Update Component', () => {
    let comp: GodzinyOtwarciaUpdateComponent;
    let fixture: ComponentFixture<GodzinyOtwarciaUpdateComponent>;
    let service: GodzinyOtwarciaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OracleTestModule],
        declarations: [GodzinyOtwarciaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(GodzinyOtwarciaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GodzinyOtwarciaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GodzinyOtwarciaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new GodzinyOtwarcia(123);
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
        const entity = new GodzinyOtwarcia();
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
