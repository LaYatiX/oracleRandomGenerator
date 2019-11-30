import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { OracleTestModule } from '../../../test.module';
import { LokalizacjaDetailComponent } from 'app/entities/lokalizacja/lokalizacja-detail.component';
import { Lokalizacja } from 'app/shared/model/lokalizacja.model';

describe('Component Tests', () => {
  describe('Lokalizacja Management Detail Component', () => {
    let comp: LokalizacjaDetailComponent;
    let fixture: ComponentFixture<LokalizacjaDetailComponent>;
    const route = ({ data: of({ lokalizacja: new Lokalizacja(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OracleTestModule],
        declarations: [LokalizacjaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(LokalizacjaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LokalizacjaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.lokalizacja).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
