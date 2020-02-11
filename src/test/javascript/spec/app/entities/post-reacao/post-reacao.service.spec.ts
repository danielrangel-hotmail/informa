import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { PostReacaoService } from 'app/entities/post-reacao/post-reacao.service';
import { IPostReacao, PostReacao } from 'app/shared/model/post-reacao.model';

describe('Service Tests', () => {
  describe('PostReacao Service', () => {
    let injector: TestBed;
    let service: PostReacaoService;
    let httpMock: HttpTestingController;
    let elemDefault: IPostReacao;
    let expectedResult: IPostReacao | IPostReacao[] | boolean | null;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(PostReacaoService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new PostReacao(0, currentDate, currentDate, 0, 'AAAAAAA');
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

      it('should create a PostReacao', () => {
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
          .create(new PostReacao())
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a PostReacao', () => {
        const returnedFromService = Object.assign(
          {
            criacao: currentDate.format(DATE_TIME_FORMAT),
            ultimaEdicao: currentDate.format(DATE_TIME_FORMAT),
            versao: 1,
            reacao: 'BBBBBB'
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

      it('should return a list of PostReacao', () => {
        const returnedFromService = Object.assign(
          {
            criacao: currentDate.format(DATE_TIME_FORMAT),
            ultimaEdicao: currentDate.format(DATE_TIME_FORMAT),
            versao: 1,
            reacao: 'BBBBBB'
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

      it('should delete a PostReacao', () => {
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
