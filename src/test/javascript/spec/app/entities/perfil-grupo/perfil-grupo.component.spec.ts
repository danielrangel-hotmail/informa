import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Data } from '@angular/router';

import { InformaTestModule } from '../../../test.module';
import { PerfilGrupoComponent } from 'app/entities/perfil-grupo/perfil-grupo.component';
import { PerfilGrupoService } from 'app/entities/perfil-grupo/perfil-grupo.service';
import { PerfilGrupo } from 'app/shared/model/perfil-grupo.model';

describe('Component Tests', () => {
  describe('PerfilGrupo Management Component', () => {
    let comp: PerfilGrupoComponent;
    let fixture: ComponentFixture<PerfilGrupoComponent>;
    let service: PerfilGrupoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [InformaTestModule],
        declarations: [PerfilGrupoComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: {
              data: {
                subscribe: (fn: (value: Data) => void) =>
                  fn({
                    pagingParams: {
                      predicate: 'id',
                      reverse: false,
                      page: 0
                    }
                  })
              }
            }
          }
        ]
      })
        .overrideTemplate(PerfilGrupoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PerfilGrupoComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PerfilGrupoService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PerfilGrupo(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.perfilGrupos && comp.perfilGrupos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.perfilGrupos && comp.perfilGrupos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should re-initialize the page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PerfilGrupo(123)],
            headers
          })
        )
      );

      // WHEN

      comp.reset();

      // THEN
      expect(service.query).toHaveBeenCalledTimes(2);
      expect(comp.perfilGrupos && comp.perfilGrupos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should calculate the sort attribute for an id', () => {
      // WHEN
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
    });
  });
});
