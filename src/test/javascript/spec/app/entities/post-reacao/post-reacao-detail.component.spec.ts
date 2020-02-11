import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { InformaTestModule } from '../../../test.module';
import { PostReacaoDetailComponent } from 'app/entities/post-reacao/post-reacao-detail.component';
import { PostReacao } from 'app/shared/model/post-reacao.model';

describe('Component Tests', () => {
  describe('PostReacao Management Detail Component', () => {
    let comp: PostReacaoDetailComponent;
    let fixture: ComponentFixture<PostReacaoDetailComponent>;
    const route = ({ data: of({ postReacao: new PostReacao(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [InformaTestModule],
        declarations: [PostReacaoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PostReacaoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PostReacaoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load postReacao on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.postReacao).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
