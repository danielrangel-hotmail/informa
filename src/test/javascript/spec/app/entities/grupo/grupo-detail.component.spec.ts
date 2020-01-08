import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { InformaTestModule } from '../../../test.module';
import { GrupoDetailComponent } from 'app/entities/grupo/grupo-detail.component';
import { Grupo } from 'app/shared/model/grupo.model';

describe('Component Tests', () => {
  describe('Grupo Management Detail Component', () => {
    let comp: GrupoDetailComponent;
    let fixture: ComponentFixture<GrupoDetailComponent>;
    const route = ({ data: of({ grupo: new Grupo(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [InformaTestModule],
        declarations: [GrupoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(GrupoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(GrupoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load grupo on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.grupo).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
