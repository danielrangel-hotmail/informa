import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { InformaTestModule } from '../../../test.module';
import { PostReacaoUpdateComponent } from 'app/entities/post-reacao/post-reacao-update.component';
import { PostReacaoService } from 'app/entities/post-reacao/post-reacao.service';
import { PostReacao } from 'app/shared/model/post-reacao.model';

describe('Component Tests', () => {
  describe('PostReacao Management Update Component', () => {
    let comp: PostReacaoUpdateComponent;
    let fixture: ComponentFixture<PostReacaoUpdateComponent>;
    let service: PostReacaoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [InformaTestModule],
        declarations: [PostReacaoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PostReacaoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PostReacaoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PostReacaoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PostReacao(123);
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
        const entity = new PostReacao();
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
