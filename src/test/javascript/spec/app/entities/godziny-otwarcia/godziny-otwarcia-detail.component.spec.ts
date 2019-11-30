import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { OracleTestModule } from '../../../test.module';
import { GodzinyOtwarciaDetailComponent } from 'app/entities/godziny-otwarcia/godziny-otwarcia-detail.component';
import { GodzinyOtwarcia } from 'app/shared/model/godziny-otwarcia.model';

describe('Component Tests', () => {
  describe('GodzinyOtwarcia Management Detail Component', () => {
    let comp: GodzinyOtwarciaDetailComponent;
    let fixture: ComponentFixture<GodzinyOtwarciaDetailComponent>;
    const route = ({ data: of({ godzinyOtwarcia: new GodzinyOtwarcia(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OracleTestModule],
        declarations: [GodzinyOtwarciaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(GodzinyOtwarciaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(GodzinyOtwarciaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.godzinyOtwarcia).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
