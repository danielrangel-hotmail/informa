import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { InformaTestModule } from '../../../test.module';
import { TopicoDetailComponent } from 'app/entities/topico/topico-detail.component';
import { Topico } from 'app/shared/model/topico.model';

describe('Component Tests', () => {
  describe('Topico Management Detail Component', () => {
    let comp: TopicoDetailComponent;
    let fixture: ComponentFixture<TopicoDetailComponent>;
    const route = ({ data: of({ topico: new Topico(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [InformaTestModule],
        declarations: [TopicoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TopicoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TopicoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load topico on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.topico).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
