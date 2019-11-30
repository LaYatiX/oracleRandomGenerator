import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { OracleTestModule } from '../../../test.module';
import { NieruchomoscComponent } from 'app/entities/nieruchomosc/nieruchomosc.component';
import { NieruchomoscService } from 'app/entities/nieruchomosc/nieruchomosc.service';
import { Nieruchomosc } from 'app/shared/model/nieruchomosc.model';

describe('Component Tests', () => {
  describe('Nieruchomosc Management Component', () => {
    let comp: NieruchomoscComponent;
    let fixture: ComponentFixture<NieruchomoscComponent>;
    let service: NieruchomoscService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OracleTestModule],
        declarations: [NieruchomoscComponent],
        providers: []
      })
        .overrideTemplate(NieruchomoscComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(NieruchomoscComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(NieruchomoscService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Nieruchomosc(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.nieruchomoscs[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
