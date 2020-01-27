import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { InformaTestModule } from '../../../test.module';
import { PerfilGrupoUpdateComponent } from 'app/entities/perfil-grupo/perfil-grupo-update.component';
import { PerfilGrupoService } from 'app/entities/perfil-grupo/perfil-grupo.service';
import { PerfilGrupo } from 'app/shared/model/perfil-grupo.model';

describe('Component Tests', () => {
  describe('PerfilGrupo Management Update Component', () => {
    let comp: PerfilGrupoUpdateComponent;
    let fixture: ComponentFixture<PerfilGrupoUpdateComponent>;
    let service: PerfilGrupoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [InformaTestModule],
        declarations: [PerfilGrupoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PerfilGrupoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PerfilGrupoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PerfilGrupoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PerfilGrupo(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new PerfilGrupo();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
