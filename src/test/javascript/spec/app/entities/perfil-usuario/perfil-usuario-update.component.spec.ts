import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { InformaTestModule } from '../../../test.module';
import { PerfilUsuarioUpdateComponent } from 'app/entities/perfil-usuario/perfil-usuario-update.component';
import { PerfilUsuarioService } from 'app/entities/perfil-usuario/perfil-usuario.service';
import { PerfilUsuario } from 'app/shared/model/perfil-usuario.model';

describe('Component Tests', () => {
  describe('PerfilUsuario Management Update Component', () => {
    let comp: PerfilUsuarioUpdateComponent;
    let fixture: ComponentFixture<PerfilUsuarioUpdateComponent>;
    let service: PerfilUsuarioService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [InformaTestModule],
        declarations: [PerfilUsuarioUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PerfilUsuarioUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PerfilUsuarioUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PerfilUsuarioService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PerfilUsuario(123);
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
        const entity = new PerfilUsuario();
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
