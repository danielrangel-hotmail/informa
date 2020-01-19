import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { MensagemService } from 'app/entities/mensagem/mensagem.service';
import { Mensagem } from 'app/shared/model/mensagem.model';
import {IMensagem} from 'app/shared/model/mensagem.interface';

describe('Service Tests', () => {
  describe('Mensagem Service', () => {
    let injector: TestBed;
    let service: MensagemService;
    let httpMock: HttpTestingController;
    let elemDefault: IMensagem;
    let expectedResult: IMensagem | IMensagem[] | boolean | null;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(MensagemService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Mensagem(0, 0, currentDate, currentDate, 'AAAAAAA', false);
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

      it('should create a Mensagem', () => {
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
          .create(new Mensagem())
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Mensagem', () => {
        const returnedFromService = Object.assign(
          {
            versao: 1,
            criacao: currentDate.format(DATE_TIME_FORMAT),
            ultimaEdicao: currentDate.format(DATE_TIME_FORMAT),
            conteudo: 'BBBBBB',
            temConversa: true
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

      it('should return a list of Mensagem', () => {
        const returnedFromService = Object.assign(
          {
            versao: 1,
            criacao: currentDate.format(DATE_TIME_FORMAT),
            ultimaEdicao: currentDate.format(DATE_TIME_FORMAT),
            conteudo: 'BBBBBB',
            temConversa: true
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
          .query(1)
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

      it('should delete a Mensagem', () => {
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
