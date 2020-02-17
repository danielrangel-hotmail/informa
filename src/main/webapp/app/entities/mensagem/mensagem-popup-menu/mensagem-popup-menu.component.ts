import { Component, Input, OnInit, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'jhi-mensagem-popup-menu',
  templateUrl: './mensagem-popup-menu.component.html',
  styleUrls: ['./mensagem-popup-menu.component.scss']
})
export class MensagemPopupMenuComponent implements OnInit {

  @Input() mensagemDoUsuario = false;
  @Output() edit = new EventEmitter();

  constructor() { }

  ngOnInit(): void {
  }

  editClicked(): void {
    this.edit.emit("clicked");
  }

}
