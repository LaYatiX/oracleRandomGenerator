import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { OracleTestModule } from '../../../test.module';
import { TransakcjaDetailComponent } from 'app/entities/transakcja/transakcja-detail.component';
import { Transakcja } from 'app/shared/model/transakcja.model';

describe('Component Tests', () => {
  describe('Transakcja Management Detail Component', () => {
    let comp: TransakcjaDetailComponent;
    let fixture: ComponentFixture<TransakcjaDetailComponent>;
    const route = ({ data: of({ transakcja: new Transakcja(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OracleTestModule],
        declarations: [TransakcjaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TransakcjaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TransakcjaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.transakcja).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
