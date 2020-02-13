import { Component, Input, OnInit } from '@angular/core';
import { IEmojiItem } from 'app/shared/emoji-list-picker/emoji-item.interface';
import { Post } from 'app/shared/model/post.model';

@Component({
  selector: 'jhi-post-reacoes',
  templateUrl: './post-reacoes.component.html',
  styleUrls: ['./post-reacoes.component.scss']
})
export class PostReacoesComponent implements OnInit {
  @Input() post!: Post;

  protected emojiList: IEmojiItem[] = [
    { emoji: 'thumbsup' },
    { emoji: 'facepunch' },
    { emoji: 'heart' },
    { emoji: 'grinning' },
    { emoji: 'cry' },
    { emoji: 'flushed' },
    { emoji: 'thumbsdown'}
  ];
  protected mostraReacoesPicker = false;

  constructor() { }

  ngOnInit(): void {
  }

  chosen(): IEmojiItem | null {
    if (this.post.reacaoLogado ===  null) return null;
    return { emoji: this.post.reacaoLogado!.reacao}
  }

  toggleMostraReacoesPicker() : void {
    this.mostraReacoesPicker = ! this.mostraReacoesPicker;
  }

  fechaEmojiListPicker(event: string) : void {
    this.mostraReacoesPicker = false;
  }

  emojiReaction(emoji: IEmojiItem): void {
    if (this.post.reacaoLogado === null) {
      this.post.reacaoLogado = { reacao: emoji.emoji}
    } else {
      this.post.reacaoLogado!.reacao  = emoji.emoji;
    }
    this.mostraReacoesPicker = false;
  }


}
