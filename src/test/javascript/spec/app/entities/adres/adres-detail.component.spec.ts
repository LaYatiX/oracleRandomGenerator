import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { OracleTestModule } from '../../../test.module';
import { AdresDetailComponent } from 'app/entities/adres/adres-detail.component';
import { Adres } from 'app/shared/model/adres.model';

describe('Component Tests', () => {
  describe('Adres Management Detail Component', () => {
    let comp: AdresDetailComponent;
    let fixture: ComponentFixture<AdresDetailComponent>;
    const route = ({ data: of({ adres: new Adres(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OracleTestModule],
        declarations: [AdresDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AdresDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AdresDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.adres).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
