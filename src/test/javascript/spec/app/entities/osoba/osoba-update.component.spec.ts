import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { OracleTestModule } from '../../../test.module';
import { OsobaUpdateComponent } from 'app/entities/osoba/osoba-update.component';
import { OsobaService } from 'app/entities/osoba/osoba.service';
import { Osoba } from 'app/shared/model/osoba.model';

describe('Component Tests', () => {
  describe('Osoba Management Update Component', () => {
    let comp: OsobaUpdateComponent;
    let fixture: ComponentFixture<OsobaUpdateComponent>;
    let service: OsobaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OracleTestModule],
        declarations: [OsobaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(OsobaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OsobaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OsobaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Osoba(123);
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
        const entity = new Osoba();
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
