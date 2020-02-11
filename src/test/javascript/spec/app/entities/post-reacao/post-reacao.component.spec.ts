import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { InformaTestModule } from '../../../test.module';
import { PostReacaoComponent } from 'app/entities/post-reacao/post-reacao.component';
import { PostReacaoService } from 'app/entities/post-reacao/post-reacao.service';
import { PostReacao } from 'app/shared/model/post-reacao.model';

describe('Component Tests', () => {
  describe('PostReacao Management Component', () => {
    let comp: PostReacaoComponent;
    let fixture: ComponentFixture<PostReacaoComponent>;
    let service: PostReacaoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [InformaTestModule],
        declarations: [PostReacaoComponent],
        providers: []
      })
        .overrideTemplate(PostReacaoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PostReacaoComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PostReacaoService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PostReacao(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.postReacaos && comp.postReacaos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
