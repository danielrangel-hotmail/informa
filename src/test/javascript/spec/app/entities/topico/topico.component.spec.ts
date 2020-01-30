import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { InformaTestModule } from '../../../test.module';
import { TopicoComponent } from 'app/entities/topico/topico.component';
import { TopicoService } from 'app/entities/topico/topico.service';
import { Topico } from 'app/shared/model/topico.model';

describe('Component Tests', () => {
  describe('Topico Management Component', () => {
    let comp: TopicoComponent;
    let fixture: ComponentFixture<TopicoComponent>;
    let service: TopicoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [InformaTestModule],
        declarations: [TopicoComponent],
        providers: []
      })
        .overrideTemplate(TopicoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TopicoComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TopicoService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Topico(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.topicos && comp.topicos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
