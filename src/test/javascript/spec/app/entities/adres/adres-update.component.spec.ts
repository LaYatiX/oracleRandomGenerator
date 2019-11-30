import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { OracleTestModule } from '../../../test.module';
import { AdresUpdateComponent } from 'app/entities/adres/adres-update.component';
import { AdresService } from 'app/entities/adres/adres.service';
import { Adres } from 'app/shared/model/adres.model';

describe('Component Tests', () => {
  describe('Adres Management Update Component', () => {
    let comp: AdresUpdateComponent;
    let fixture: ComponentFixture<AdresUpdateComponent>;
    let service: AdresService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OracleTestModule],
        declarations: [AdresUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AdresUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AdresUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AdresService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Adres(123);
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
        const entity = new Adres();
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
