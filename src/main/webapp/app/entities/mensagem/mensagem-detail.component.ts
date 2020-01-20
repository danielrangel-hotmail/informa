import { Component, OnInit, Input } from '@angular/core';
import {IPost} from 'app/shared/model/post.interface';
import * as moment from 'moment';
import {IMensagem} from 'app/shared/model/mensagem.interface';

@Component({
  selector: 'jhi-mensagem-detail',
  templateUrl: './mensagem-detail.component.html',
  styleUrls: [ './mensagem-detail.component.scss' ]
})
export class MensagemDetailComponent implements OnInit {
  @Input() mensagem: IMensagem | null = null;

  constructor() {}

  ngOnInit(): void {
  }

  previousState(): void {
    window.history.back();
  }

  tempoRelativo(mensagem: IMensagem): string {
    const ultimaDataRelevante = mensagem.ultimaEdicao;
    ultimaDataRelevante!.locale("pt-br");
    const duration: moment.Duration = moment.duration(ultimaDataRelevante!.diff(moment()));
    if (duration.asDays() < 1) return ultimaDataRelevante!.fromNow();
    return ultimaDataRelevante!.calendar();
  }

}
