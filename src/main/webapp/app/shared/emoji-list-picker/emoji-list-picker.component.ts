import { Component, ElementRef, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { IEmojiItem } from 'app/shared/emoji-list-picker/emoji-item.interface';

@Component({
  selector: 'jhi-emoji-list-picker',
  templateUrl: './emoji-list-picker.component.html',
  styleUrls: ['./emoji-list-picker.component.scss']
})
export class EmojiListPickerComponent implements OnInit {

  @Input() emojiList: IEmojiItem[] = [];
  @Input() chosen: IEmojiItem | null = null;
  @Output() cancel = new EventEmitter();
  @Output() picked = new EventEmitter();


  private first = true;

  constructor(private eRef: ElementRef) { }

  ngOnInit(): void {
  }

  clickout(): void {
    if (this.first) {
      this.first = false;
      return;
    }
  this.cancel.emit("canceled");
  }

  emojiPicked(emoji: IEmojiItem): void {
    this.picked.emit(emoji);
  }

  emojiPickedRemoved(): void {
    this.picked.emit(undefined);
  }

}
