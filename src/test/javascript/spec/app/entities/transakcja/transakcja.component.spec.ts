import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { OracleTestModule } from '../../../test.module';
import { TransakcjaComponent } from 'app/entities/transakcja/transakcja.component';
import { TransakcjaService } from 'app/entities/transakcja/transakcja.service';
import { Transakcja } from 'app/shared/model/transakcja.model';

describe('Component Tests', () => {
  describe('Transakcja Management Component', () => {
    let comp: TransakcjaComponent;
    let fixture: ComponentFixture<TransakcjaComponent>;
    let service: TransakcjaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OracleTestModule],
        declarations: [TransakcjaComponent],
        providers: []
      })
        .overrideTemplate(TransakcjaComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TransakcjaComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TransakcjaService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Transakcja(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.transakcjas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
