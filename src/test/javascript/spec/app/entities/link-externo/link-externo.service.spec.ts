import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { LinkExternoService } from 'app/entities/link-externo/link-externo.service';
import { ILinkExterno, LinkExterno } from 'app/shared/model/link-externo.model';
import { LinkTipo } from 'app/shared/model/enumerations/link-tipo.model';

describe('Service Tests', () => {
  describe('LinkExterno Service', () => {
    let injector: TestBed;
    let service: LinkExternoService;
    let httpMock: HttpTestingController;
    let elemDefault: ILinkExterno;
    let expectedResult: ILinkExterno | ILinkExterno[] | boolean | null;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(LinkExternoService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new LinkExterno(0, 0, currentDate, currentDate, LinkTipo.VIDEO, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            criacao: currentDate.format(DATE_TIME_FORMAT),
            ultimaEdicao: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a LinkExterno', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            criacao: currentDate.format(DATE_TIME_FORMAT),
            ultimaEdicao: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            criacao: currentDate,
            ultimaEdicao: currentDate
          },
          returnedFromService
        );
        service
          .create(new LinkExterno())
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a LinkExterno', () => {
        const returnedFromService = Object.assign(
          {
            versao: 1,
            criacao: currentDate.format(DATE_TIME_FORMAT),
            ultimaEdicao: currentDate.format(DATE_TIME_FORMAT),
            tipo: 'BBBBBB',
            link: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            criacao: currentDate,
            ultimaEdicao: currentDate
          },
          returnedFromService
        );
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of LinkExterno', () => {
        const returnedFromService = Object.assign(
          {
            versao: 1,
            criacao: currentDate.format(DATE_TIME_FORMAT),
            ultimaEdicao: currentDate.format(DATE_TIME_FORMAT),
            tipo: 'BBBBBB',
            link: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            criacao: currentDate,
            ultimaEdicao: currentDate
          },
          returnedFromService
        );
        service
          .query()
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

      it('should delete a LinkExterno', () => {
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
