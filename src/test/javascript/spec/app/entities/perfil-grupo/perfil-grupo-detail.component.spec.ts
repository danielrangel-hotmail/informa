import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { InformaTestModule } from '../../../test.module';
import { PerfilGrupoDetailComponent } from 'app/entities/perfil-grupo/perfil-grupo-detail.component';
import { PerfilGrupo } from 'app/shared/model/perfil-grupo.model';

describe('Component Tests', () => {
  describe('PerfilGrupo Management Detail Component', () => {
    let comp: PerfilGrupoDetailComponent;
    let fixture: ComponentFixture<PerfilGrupoDetailComponent>;
    const route = ({ data: of({ perfilGrupo: new PerfilGrupo(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [InformaTestModule],
        declarations: [PerfilGrupoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PerfilGrupoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PerfilGrupoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load perfilGrupo on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.perfilGrupo).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
