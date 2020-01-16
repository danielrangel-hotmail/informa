import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { InformaTestModule } from '../../../test.module';
import { LinkExternoDetailComponent } from 'app/entities/link-externo/link-externo-detail.component';
import { LinkExterno } from 'app/shared/model/link-externo.model';

describe('Component Tests', () => {
  describe('LinkExterno Management Detail Component', () => {
    let comp: LinkExternoDetailComponent;
    let fixture: ComponentFixture<LinkExternoDetailComponent>;
    const route = ({ data: of({ linkExterno: new LinkExterno(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [InformaTestModule],
        declarations: [LinkExternoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(LinkExternoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LinkExternoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load linkExterno on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.linkExterno).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
