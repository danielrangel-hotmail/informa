import { Component, Input, OnInit } from '@angular/core';
import { IEmojiItem } from 'app/shared/emoji-list-picker/emoji-item.interface';
import { Post } from 'app/shared/model/post.model';
import { PostReacaoService } from 'app/entities/post-reacao/post-reacao.service';
import { Observable } from 'rxjs';
import { HttpResponse } from '@angular/common/http';
import { IPostReacoes } from 'app/shared/model/post-reacoes.interface';

@Component({
  selector: 'jhi-post-reacoes',
  templateUrl: './post-reacoes.component.html',
  styleUrls: ['./post-reacoes.component.scss']
})
export class PostReacoesComponent implements OnInit {
  @Input() post!: Post;

  emojiList: IEmojiItem[] = [
    { emoji: 'thumbsup' },
    { emoji: 'facepunch' },
    { emoji: 'heart' },
    { emoji: 'grinning' },
    { emoji: 'cry' },
    { emoji: 'flushed' },
    { emoji: 'thumbsdown'}
  ];
  mostraReacoesPicker = false;

  constructor(protected postReacaoService: PostReacaoService) { }

  ngOnInit(): void {
  }

  chosen(): IEmojiItem | null {
    if (!this.post.reacoes!.reacaoLogado) return null;
    return { emoji: this.post.reacoes!.reacaoLogado.reacao}
  }

  toggleMostraReacoesPicker() : void {
    this.mostraReacoesPicker = ! this.mostraReacoesPicker;
  }

  fechaEmojiListPicker() : void {
    this.mostraReacoesPicker = false;
  }

  emojiReaction(emoji: IEmojiItem): void {
    this.mostraReacoesPicker = false;
    if (!this.post.reacoes!.reacaoLogado) {
      if (!emoji) return;
      const criaReacao = {
        reacao: emoji.emoji,
        postId: this.post.id
      };
      this.subscribeToSaveResponse(this.postReacaoService.create(criaReacao));
    } else {
      if (!emoji) {
        this.subscribeToSaveResponse(this.postReacaoService.delete(this.post.reacoes!.reacaoLogado.id!));
        return;
      }
      const reacaoAlterada  = { ... this.post.reacoes!.reacaoLogado, reacao: emoji.emoji };
      this.subscribeToSaveResponse(this.postReacaoService.update(reacaoAlterada))
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPostReacoes>>): void {
    result.subscribe(
      (postReacaoResponse) => this.post.reacoes = postReacaoResponse.body !== null ? postReacaoResponse.body : undefined
    );
  }





}
