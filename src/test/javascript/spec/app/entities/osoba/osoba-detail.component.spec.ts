import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { OracleTestModule } from '../../../test.module';
import { OsobaDetailComponent } from 'app/entities/osoba/osoba-detail.component';
import { Osoba } from 'app/shared/model/osoba.model';

describe('Component Tests', () => {
  describe('Osoba Management Detail Component', () => {
    let comp: OsobaDetailComponent;
    let fixture: ComponentFixture<OsobaDetailComponent>;
    const route = ({ data: of({ osoba: new Osoba(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OracleTestModule],
        declarations: [OsobaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(OsobaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OsobaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.osoba).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
