import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { PerfilUsuarioService } from 'app/entities/perfil-usuario/perfil-usuario.service';
import { IPerfilUsuario, PerfilUsuario } from 'app/shared/model/perfil-usuario.model';

describe('Service Tests', () => {
  describe('PerfilUsuario Service', () => {
    let injector: TestBed;
    let service: PerfilUsuarioService;
    let httpMock: HttpTestingController;
    let elemDefault: IPerfilUsuario;
    let expectedResult: IPerfilUsuario | IPerfilUsuario[] | boolean | null;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(PerfilUsuarioService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new PerfilUsuario(0, currentDate, currentDate, 0, currentDate, currentDate, currentDate, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            criacao: currentDate.format(DATE_TIME_FORMAT),
            ultimaEdicao: currentDate.format(DATE_TIME_FORMAT),
            entradaNaEmpresa: currentDate.format(DATE_FORMAT),
            saidaDaEmpresa: currentDate.format(DATE_FORMAT),
            nascimento: currentDate.format(DATE_FORMAT)
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

      it('should create a PerfilUsuario', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            criacao: currentDate.format(DATE_TIME_FORMAT),
            ultimaEdicao: currentDate.format(DATE_TIME_FORMAT),
            entradaNaEmpresa: currentDate.format(DATE_FORMAT),
            saidaDaEmpresa: currentDate.format(DATE_FORMAT),
            nascimento: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            criacao: currentDate,
            ultimaEdicao: currentDate,
            entradaNaEmpresa: currentDate,
            saidaDaEmpresa: currentDate,
            nascimento: currentDate
          },
          returnedFromService
        );
        service
          .create(new PerfilUsuario())
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a PerfilUsuario', () => {
        const returnedFromService = Object.assign(
          {
            criacao: currentDate.format(DATE_TIME_FORMAT),
            ultimaEdicao: currentDate.format(DATE_TIME_FORMAT),
            versao: 1,
            entradaNaEmpresa: currentDate.format(DATE_FORMAT),
            saidaDaEmpresa: currentDate.format(DATE_FORMAT),
            nascimento: currentDate.format(DATE_FORMAT),
            skype: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            criacao: currentDate,
            ultimaEdicao: currentDate,
            entradaNaEmpresa: currentDate,
            saidaDaEmpresa: currentDate,
            nascimento: currentDate
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

      it('should return a list of PerfilUsuario', () => {
        const returnedFromService = Object.assign(
          {
            criacao: currentDate.format(DATE_TIME_FORMAT),
            ultimaEdicao: currentDate.format(DATE_TIME_FORMAT),
            versao: 1,
            entradaNaEmpresa: currentDate.format(DATE_FORMAT),
            saidaDaEmpresa: currentDate.format(DATE_FORMAT),
            nascimento: currentDate.format(DATE_FORMAT),
            skype: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            criacao: currentDate,
            ultimaEdicao: currentDate,
            entradaNaEmpresa: currentDate,
            saidaDaEmpresa: currentDate,
            nascimento: currentDate
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

      it('should delete a PerfilUsuario', () => {
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
