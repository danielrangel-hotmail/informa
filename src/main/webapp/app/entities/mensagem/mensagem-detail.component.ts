import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import * as moment from 'moment';
import {IMensagem} from '../../shared/model/mensagem.interface';

@Component({
  selector: 'jhi-mensagem-detail',
  templateUrl: './mensagem-detail.component.html',
  styleUrls: [ './mensagem-detail.component.scss' ]
})
export class MensagemDetailComponent implements OnInit {
  @Input() mensagem: IMensagem | null = null;
  @Input() account?: Account;
  @Output() editMensagem = new EventEmitter<IMensagem>();

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

  mensagemDoUsuario(): boolean {
    return this.mensagemAutorId() === this.accountId();
  }
  mensagemAutorId(): string {
    return this.mensagem ? ( this.mensagem.autorId ? this.mensagem.autorId.toString() : "" ) : "";
  }

  accountId(): string {
    return this.account ? (this.account.id ? this.account.id.toString() : "") : "";
  }

  editClicked(): void {
    this.editMensagem.emit(this.mensagem!);
  }


}
