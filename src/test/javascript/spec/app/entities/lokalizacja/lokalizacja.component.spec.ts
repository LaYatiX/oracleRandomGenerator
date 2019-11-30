import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { OracleTestModule } from '../../../test.module';
import { LokalizacjaComponent } from 'app/entities/lokalizacja/lokalizacja.component';
import { LokalizacjaService } from 'app/entities/lokalizacja/lokalizacja.service';
import { Lokalizacja } from 'app/shared/model/lokalizacja.model';

describe('Component Tests', () => {
  describe('Lokalizacja Management Component', () => {
    let comp: LokalizacjaComponent;
    let fixture: ComponentFixture<LokalizacjaComponent>;
    let service: LokalizacjaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OracleTestModule],
        declarations: [LokalizacjaComponent],
        providers: []
      })
        .overrideTemplate(LokalizacjaComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LokalizacjaComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LokalizacjaService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Lokalizacja(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.lokalizacjas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
