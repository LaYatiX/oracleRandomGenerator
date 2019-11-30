import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { OracleTestModule } from '../../../test.module';
import { AdresComponent } from 'app/entities/adres/adres.component';
import { AdresService } from 'app/entities/adres/adres.service';
import { Adres } from 'app/shared/model/adres.model';

describe('Component Tests', () => {
  describe('Adres Management Component', () => {
    let comp: AdresComponent;
    let fixture: ComponentFixture<AdresComponent>;
    let service: AdresService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OracleTestModule],
        declarations: [AdresComponent],
        providers: []
      })
        .overrideTemplate(AdresComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AdresComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AdresService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Adres(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.adres[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
