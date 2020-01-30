import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { InformaTestModule } from '../../../test.module';
import { PerfilUsuarioDetailComponent } from 'app/entities/perfil-usuario/perfil-usuario-detail.component';
import { PerfilUsuario } from 'app/shared/model/perfil-usuario.model';

describe('Component Tests', () => {
  describe('PerfilUsuario Management Detail Component', () => {
    let comp: PerfilUsuarioDetailComponent;
    let fixture: ComponentFixture<PerfilUsuarioDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ perfilUsuario: new PerfilUsuario(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [InformaTestModule],
        declarations: [PerfilUsuarioDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PerfilUsuarioDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PerfilUsuarioDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load perfilUsuario on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.perfilUsuario).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });

    describe('byteSize', () => {
      it('Should call byteSize from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'byteSize');
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.byteSize(fakeBase64);

        // THEN
        expect(dataUtils.byteSize).toBeCalledWith(fakeBase64);
      });
    });

    describe('openFile', () => {
      it('Should call openFile from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'openFile');
        const fakeContentType = 'fake content type';
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.openFile(fakeContentType, fakeBase64);

        // THEN
        expect(dataUtils.openFile).toBeCalledWith(fakeContentType, fakeBase64);
      });
    });
  });
});
