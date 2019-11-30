import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { OracleTestModule } from '../../../test.module';
import { SklepDetailComponent } from 'app/entities/sklep/sklep-detail.component';
import { Sklep } from 'app/shared/model/sklep.model';

describe('Component Tests', () => {
  describe('Sklep Management Detail Component', () => {
    let comp: SklepDetailComponent;
    let fixture: ComponentFixture<SklepDetailComponent>;
    const route = ({ data: of({ sklep: new Sklep(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OracleTestModule],
        declarations: [SklepDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SklepDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SklepDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.sklep).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
