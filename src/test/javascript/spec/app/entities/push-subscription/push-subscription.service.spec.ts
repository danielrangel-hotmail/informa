import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { PushSubscriptionService } from 'app/entities/push-subscription/push-subscription.service';
import { IPushSubscription, PushSubscription } from 'app/shared/model/push-subscription.model';

describe('Service Tests', () => {
  describe('PushSubscription Service', () => {
    let injector: TestBed;
    let service: PushSubscriptionService;
    let httpMock: HttpTestingController;
    let elemDefault: IPushSubscription;
    let expectedResult: IPushSubscription | IPushSubscription[] | boolean | null;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(PushSubscriptionService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new PushSubscription(0, 0, currentDate, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            criacao: currentDate.format(DATE_TIME_FORMAT)
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

      it('should create a PushSubscription', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            criacao: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            criacao: currentDate
          },
          returnedFromService
        );
        service
          .create(new PushSubscription())
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a PushSubscription', () => {
        const returnedFromService = Object.assign(
          {
            versao: 1,
            criacao: currentDate.format(DATE_TIME_FORMAT),
            endpoint: 'BBBBBB',
            key: 'BBBBBB',
            auth: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            criacao: currentDate
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

      it('should return a list of PushSubscription', () => {
        const returnedFromService = Object.assign(
          {
            versao: 1,
            criacao: currentDate.format(DATE_TIME_FORMAT),
            endpoint: 'BBBBBB',
            key: 'BBBBBB',
            auth: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            criacao: currentDate
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

      it('should delete a PushSubscription', () => {
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
