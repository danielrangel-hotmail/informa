import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { PerfilGrupoService } from 'app/entities/perfil-grupo/perfil-grupo.service';
import { IPerfilGrupo, PerfilGrupo } from 'app/shared/model/perfil-grupo.model';

describe('Service Tests', () => {
  describe('PerfilGrupo Service', () => {
    let injector: TestBed;
    let service: PerfilGrupoService;
    let httpMock: HttpTestingController;
    let elemDefault: IPerfilGrupo;
    let expectedResult: IPerfilGrupo | IPerfilGrupo[] | boolean | null;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(PerfilGrupoService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new PerfilGrupo(0, currentDate, currentDate, 0, false, false);
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

      it('should create a PerfilGrupo', () => {
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
          .create(new PerfilGrupo())
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a PerfilGrupo', () => {
        const returnedFromService = Object.assign(
          {
            criacao: currentDate.format(DATE_TIME_FORMAT),
            ultimaEdicao: currentDate.format(DATE_TIME_FORMAT),
            versao: 1,
            favorito: true,
            notifica: true
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

      it('should return a list of PerfilGrupo', () => {
        const returnedFromService = Object.assign(
          {
            criacao: currentDate.format(DATE_TIME_FORMAT),
            ultimaEdicao: currentDate.format(DATE_TIME_FORMAT),
            versao: 1,
            favorito: true,
            notifica: true
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

      it('should delete a PerfilGrupo', () => {
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
