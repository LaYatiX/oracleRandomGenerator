import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { OracleTestModule } from '../../../test.module';
import { MieszkanieComponent } from 'app/entities/mieszkanie/mieszkanie.component';
import { MieszkanieService } from 'app/entities/mieszkanie/mieszkanie.service';
import { Mieszkanie } from 'app/shared/model/mieszkanie.model';

describe('Component Tests', () => {
  describe('Mieszkanie Management Component', () => {
    let comp: MieszkanieComponent;
    let fixture: ComponentFixture<MieszkanieComponent>;
    let service: MieszkanieService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OracleTestModule],
        declarations: [MieszkanieComponent],
        providers: []
      })
        .overrideTemplate(MieszkanieComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MieszkanieComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MieszkanieService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Mieszkanie(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.mieszkanies[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
