import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { InformaTestModule } from '../../../test.module';
import { TopicoUpdateComponent } from 'app/entities/topico/topico-update.component';
import { TopicoService } from 'app/entities/topico/topico.service';
import { Topico } from 'app/shared/model/topico.model';

describe('Component Tests', () => {
  describe('Topico Management Update Component', () => {
    let comp: TopicoUpdateComponent;
    let fixture: ComponentFixture<TopicoUpdateComponent>;
    let service: TopicoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [InformaTestModule],
        declarations: [TopicoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TopicoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TopicoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TopicoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Topico(123);
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
        const entity = new Topico();
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
