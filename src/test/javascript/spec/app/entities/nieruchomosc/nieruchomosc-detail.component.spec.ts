import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { OracleTestModule } from '../../../test.module';
import { NieruchomoscDetailComponent } from 'app/entities/nieruchomosc/nieruchomosc-detail.component';
import { Nieruchomosc } from 'app/shared/model/nieruchomosc.model';

describe('Component Tests', () => {
  describe('Nieruchomosc Management Detail Component', () => {
    let comp: NieruchomoscDetailComponent;
    let fixture: ComponentFixture<NieruchomoscDetailComponent>;
    const route = ({ data: of({ nieruchomosc: new Nieruchomosc(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OracleTestModule],
        declarations: [NieruchomoscDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(NieruchomoscDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(NieruchomoscDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.nieruchomosc).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
