import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { OracleTestModule } from '../../../test.module';
import { OsobaComponent } from 'app/entities/osoba/osoba.component';
import { OsobaService } from 'app/entities/osoba/osoba.service';
import { Osoba } from 'app/shared/model/osoba.model';

describe('Component Tests', () => {
  describe('Osoba Management Component', () => {
    let comp: OsobaComponent;
    let fixture: ComponentFixture<OsobaComponent>;
    let service: OsobaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OracleTestModule],
        declarations: [OsobaComponent],
        providers: []
      })
        .overrideTemplate(OsobaComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OsobaComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OsobaService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Osoba(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.osobas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
