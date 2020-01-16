import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { InformaTestModule } from '../../../test.module';
import { LinkExternoUpdateComponent } from 'app/entities/link-externo/link-externo-update.component';
import { LinkExternoService } from 'app/entities/link-externo/link-externo.service';
import { LinkExterno } from 'app/shared/model/link-externo.model';

describe('Component Tests', () => {
  describe('LinkExterno Management Update Component', () => {
    let comp: LinkExternoUpdateComponent;
    let fixture: ComponentFixture<LinkExternoUpdateComponent>;
    let service: LinkExternoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [InformaTestModule],
        declarations: [LinkExternoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(LinkExternoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LinkExternoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LinkExternoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new LinkExterno(123);
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
        const entity = new LinkExterno();
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
