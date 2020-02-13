import { Component, Input } from '@angular/core';
import { IPost } from '../../../shared/model/post.interface';
import {PostPublicaDialogComponent} from 'app/entities/shared-post/post-publica-dialog.component';
import {PostDeleteDialogComponent} from 'app/entities/shared-post/post-delete-dialog.component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import * as moment from 'moment';

@Component({
  selector: 'jhi-post-single',
  templateUrl: './post-single.component.html',
  styleUrls: ['./post-single.component.scss']
})
export class PostSingleComponent {
@Input() post?: IPost;
@Input() account?: Account;

  constructor(protected modalService: NgbModal) { }
  publica(post: IPost): void {
    const modalRef = this.modalService.open(PostPublicaDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.post = post;
  }

  delete(post: IPost): void {
    const modalRef = this.modalService.open(PostDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.post = post;
  }

  postAutorId(): string {
    return this.post ? ( this.post.autorId ? this.post.autorId.toString() : "" ) : "";
  }
  accountId(): string {
    return this.account ? (this.account.id ? this.account.id.toString() : "") : "";
  }

  autorEUser():boolean {
    return this.postAutorId() === this.accountId();
  }

  tempoRelativo(post: IPost): string {
    const ultimaDataRelevante = post.publicacao ? post.publicacao : post.ultimaEdicao;
    ultimaDataRelevante!.locale("pt-br");
    const duration: moment.Duration = moment.duration(ultimaDataRelevante!.diff(moment()));
    if (duration.asDays() > 1) return ultimaDataRelevante!.fromNow();
    return ultimaDataRelevante!.calendar();
  }

  numeroDeMensagens(): string {
    if (this.post!.numeroDeMensagens! === 0) return "Sem comentários";
    if (this.post!.numeroDeMensagens! === 1) return "1 comentário";
    return `${this.post!.numeroDeMensagens!} comentários`;
  }
}
