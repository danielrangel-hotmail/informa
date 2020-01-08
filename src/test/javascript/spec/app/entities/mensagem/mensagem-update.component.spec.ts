import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { InformaTestModule } from '../../../test.module';
import { MensagemUpdateComponent } from 'app/entities/mensagem/mensagem-update.component';
import { MensagemService } from 'app/entities/mensagem/mensagem.service';
import { Mensagem } from 'app/shared/model/mensagem.model';

describe('Component Tests', () => {
  describe('Mensagem Management Update Component', () => {
    let comp: MensagemUpdateComponent;
    let fixture: ComponentFixture<MensagemUpdateComponent>;
    let service: MensagemService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [InformaTestModule],
        declarations: [MensagemUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(MensagemUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MensagemUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MensagemService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Mensagem(123);
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
        const entity = new Mensagem();
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
