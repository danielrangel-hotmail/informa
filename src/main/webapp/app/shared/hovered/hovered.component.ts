import { Component, OnInit, Input, HostListener } from '@angular/core';

@Component({
  selector: 'jhi-hovered',
  template: `
      <div class = "div-hovered"
        [ngStyle]="isHovered ? hoverStyle : null"
      >
        <ng-content></ng-content>
      </div>
      `
})
export class HoveredComponent implements OnInit {
@Input() hoverStyle?: any;
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
