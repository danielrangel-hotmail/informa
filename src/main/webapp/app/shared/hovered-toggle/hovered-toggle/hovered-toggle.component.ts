import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'jhi-hovered-toggle',
  templateUrl: './hovered-toggle.component.html',
  styleUrls: ['./hovered-toggle.component.scss']
})
export class HoveredToggleComponent implements OnInit {
  estouIn = false;

  constructor() { }

  ngOnInit(): void {
  }

}
