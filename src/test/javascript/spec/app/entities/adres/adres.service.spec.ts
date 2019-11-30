import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import { AdresService } from 'app/entities/adres/adres.service';
import { IAdres, Adres } from 'app/shared/model/adres.model';

describe('Service Tests', () => {
  describe('Adres Service', () => {
    let injector: TestBed;
    let service: AdresService;
    let httpMock: HttpTestingController;
    let elemDefault: IAdres;
    let expectedResult;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(AdresService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Adres(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a Adres', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .create(new Adres(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Adres', () => {
        const returnedFromService = Object.assign(
          {
            miasto: 'BBBBBB',
            ulica: 'BBBBBB',
            nrDomu: 'BBBBBB',
            nrLokalu: 'BBBBBB',
            wojewodzwtwo: 'BBBBBB',
            powiat: 'BBBBBB',
            gmina: 'BBBBBB',
            kodPocztowy: 'BBBBBB',
            kraj: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of Adres', () => {
        const returnedFromService = Object.assign(
          {
            miasto: 'BBBBBB',
            ulica: 'BBBBBB',
            nrDomu: 'BBBBBB',
            nrLokalu: 'BBBBBB',
            wojewodzwtwo: 'BBBBBB',
            powiat: 'BBBBBB',
            gmina: 'BBBBBB',
            kodPocztowy: 'BBBBBB',
            kraj: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Adres', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
