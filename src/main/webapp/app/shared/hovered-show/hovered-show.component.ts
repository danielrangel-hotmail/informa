import { Component, HostListener, Input, OnInit } from '@angular/core';

@Component({
  selector: 'jhi-hovered-show',
  templateUrl: './hovered-show.component.html',
  styleUrls: ['./hovered-show.component.scss']
})
export class HoveredShowComponent implements OnInit {
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
