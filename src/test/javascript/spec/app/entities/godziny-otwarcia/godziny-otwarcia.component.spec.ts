import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { OracleTestModule } from '../../../test.module';
import { GodzinyOtwarciaComponent } from 'app/entities/godziny-otwarcia/godziny-otwarcia.component';
import { GodzinyOtwarciaService } from 'app/entities/godziny-otwarcia/godziny-otwarcia.service';
import { GodzinyOtwarcia } from 'app/shared/model/godziny-otwarcia.model';

describe('Component Tests', () => {
  describe('GodzinyOtwarcia Management Component', () => {
    let comp: GodzinyOtwarciaComponent;
    let fixture: ComponentFixture<GodzinyOtwarciaComponent>;
    let service: GodzinyOtwarciaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OracleTestModule],
        declarations: [GodzinyOtwarciaComponent],
        providers: []
      })
        .overrideTemplate(GodzinyOtwarciaComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GodzinyOtwarciaComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GodzinyOtwarciaService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new GodzinyOtwarcia(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.godzinyOtwarcias[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
