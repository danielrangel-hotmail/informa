import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { GrupoService } from 'app/entities/grupo/grupo.service';
import { Grupo } from 'app/shared/model/grupo.model';
import { IGrupo } from 'app/shared/model/grupo.interface';

describe('Service Tests', () => {
  describe('Grupo Service', () => {
    let injector: TestBed;
    let service: GrupoService;
    let httpMock: HttpTestingController;
    let elemDefault: IGrupo;
    let expectedResult: IGrupo | IGrupo[] | boolean | null;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(GrupoService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Grupo(
        0,
        0,
        currentDate,
        currentDate,
        'AAAAAAA',
        'AAAAAAA',
        false,
        false,
        'image/png',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA'
      );
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

      it('should create a Grupo', () => {
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
          .create(new Grupo())
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Grupo', () => {
        const returnedFromService = Object.assign(
          {
            versao: 1,
            criacao: currentDate.format(DATE_TIME_FORMAT),
            ultimaEdicao: currentDate.format(DATE_TIME_FORMAT),
            nome: 'BBBBBB',
            descricao: 'BBBBBB',
            formal: true,
            opcional: true,
            logo: 'BBBBBB',
            cabecalhoSuperiorCor: 'BBBBBB',
            cabecalhoInferiorCor: 'BBBBBB',
            logoFundoCor: 'BBBBBB'
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

      it('should return a list of Grupo', () => {
        const returnedFromService = Object.assign(
          {
            versao: 1,
            criacao: currentDate.format(DATE_TIME_FORMAT),
            ultimaEdicao: currentDate.format(DATE_TIME_FORMAT),
            nome: 'BBBBBB',
            descricao: 'BBBBBB',
            formal: true,
            opcional: true,
            logo: 'BBBBBB',
            cabecalhoSuperiorCor: 'BBBBBB',
            cabecalhoInferiorCor: 'BBBBBB',
            logoFundoCor: 'BBBBBB'
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

      it('should delete a Grupo', () => {
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
