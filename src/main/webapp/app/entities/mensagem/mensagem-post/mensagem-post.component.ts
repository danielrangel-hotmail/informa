import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import {IPost} from 'app/shared/model/post.interface';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

@Component({
  selector: 'jhi-mensagem-post',
  templateUrl: './mensagem-post.component.html',
  styleUrls: ['./mensagem-post.component.scss']
})
export class MensagemPostComponent implements OnInit {
  post$?: Observable<IPost>;

  constructor(protected activatedRoute: ActivatedRoute) { }

  ngOnInit(): void {
    this.post$ = this.activatedRoute.data.pipe(map(data => data.post));
  }

}
