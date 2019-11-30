import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { OracleTestModule } from '../../../test.module';
import { SklepComponent } from 'app/entities/sklep/sklep.component';
import { SklepService } from 'app/entities/sklep/sklep.service';
import { Sklep } from 'app/shared/model/sklep.model';

describe('Component Tests', () => {
  describe('Sklep Management Component', () => {
    let comp: SklepComponent;
    let fixture: ComponentFixture<SklepComponent>;
    let service: SklepService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OracleTestModule],
        declarations: [SklepComponent],
        providers: []
      })
        .overrideTemplate(SklepComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SklepComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SklepService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Sklep(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.skleps[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
