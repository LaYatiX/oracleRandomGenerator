import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { OracleTestModule } from '../../../test.module';
import { NieruchomoscUpdateComponent } from 'app/entities/nieruchomosc/nieruchomosc-update.component';
import { NieruchomoscService } from 'app/entities/nieruchomosc/nieruchomosc.service';
import { Nieruchomosc } from 'app/shared/model/nieruchomosc.model';

describe('Component Tests', () => {
  describe('Nieruchomosc Management Update Component', () => {
    let comp: NieruchomoscUpdateComponent;
    let fixture: ComponentFixture<NieruchomoscUpdateComponent>;
    let service: NieruchomoscService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OracleTestModule],
        declarations: [NieruchomoscUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(NieruchomoscUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(NieruchomoscUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(NieruchomoscService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Nieruchomosc(123);
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
        const entity = new Nieruchomosc();
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
