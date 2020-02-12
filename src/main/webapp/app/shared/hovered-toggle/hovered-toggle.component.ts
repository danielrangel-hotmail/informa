import { Component, HostListener, Input, OnInit } from '@angular/core';

@Component({
  selector: 'jhi-hovered-toggle',
  templateUrl: './hovered-toggle.component.html',
  styleUrls: ['./hovered-toggle.component.scss']
})
export class HoveredToggleComponent implements OnInit {

  @Input() alwaysIn = false;

  isHovered = false;

  constructor() { }

  @HostListener('mouseenter')
  onMouseEnter(): void {
    this.isHovered = true;
  }

  @HostListener('mouseleave')
  onMouseLeave(): void {
    this.isHovered = false;
  }
  ngOnInit(): void {
  }

}
