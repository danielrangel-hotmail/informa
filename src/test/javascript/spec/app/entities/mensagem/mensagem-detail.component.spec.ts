import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { InformaTestModule } from '../../../test.module';
import { MensagemDetailComponent } from 'app/entities/mensagem/mensagem-detail.component';
import { Mensagem } from 'app/shared/model/mensagem.model';

describe('Component Tests', () => {
  describe('Mensagem Management Detail Component', () => {
    let comp: MensagemDetailComponent;
    let fixture: ComponentFixture<MensagemDetailComponent>;
    const route = ({ data: of({ mensagem: new Mensagem(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [InformaTestModule],
        declarations: [MensagemDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(MensagemDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MensagemDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load mensagem on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.mensagem).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
