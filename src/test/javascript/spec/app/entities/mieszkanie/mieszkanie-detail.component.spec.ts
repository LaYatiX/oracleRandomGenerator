import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { OracleTestModule } from '../../../test.module';
import { MieszkanieDetailComponent } from 'app/entities/mieszkanie/mieszkanie-detail.component';
import { Mieszkanie } from 'app/shared/model/mieszkanie.model';

describe('Component Tests', () => {
  describe('Mieszkanie Management Detail Component', () => {
    let comp: MieszkanieDetailComponent;
    let fixture: ComponentFixture<MieszkanieDetailComponent>;
    const route = ({ data: of({ mieszkanie: new Mieszkanie(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OracleTestModule],
        declarations: [MieszkanieDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(MieszkanieDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MieszkanieDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.mieszkanie).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
