import { Component, OnInit, Input } from '@angular/core';
import { IPost } from 'app/shared/model/post.interface';
import { NgxGalleryImage, NgxGalleryOptions } from 'ngx-gallery';
import {AMAZON_S3_BUCKET_URL} from 'app/entities/arquivo/arquivo.constants';
import {IArquivo} from 'app/shared/model/arquivo.model';

@Component({
  selector: 'jhi-post-image-view',
  templateUrl: './post-image-view.component.html',
  styleUrls: ['./post-image-view.component.scss']
})
export class PostImageViewComponent implements OnInit {
  @Input() post?: IPost;
  galleryOptions: NgxGalleryOptions[] = [];
  bucketUrl = AMAZON_S3_BUCKET_URL;
  galleryImagesParaPost: NgxGalleryImage[] = [];

  constructor() {
  }

  galeriaImages(): NgxGalleryImage[] {
    return this.galleryImagesParaPost;
  }

  ngOnInit(): void {
    if ((!this.post) || (!this.post.arquivos)) {
      return;
    }
    this.galleryImagesParaPost = this.post.arquivos.map(  (arquivo) => (
      {
        small: this.bucketUrl+arquivo.link,
        medium: this.bucketUrl+arquivo.link,
        big: this.bucketUrl+arquivo.link,
      }
    ));

    switch (this.post.arquivos.length) {
      case 1:
        this.galleryOptions = [
          {
            width: '558px',
            thumbnails: false
          }
        ];
        break;
      case 2:
        this.galleryOptions = [
          {
            width: '558px',
            thumbnailsRows: 1,
            thumbnailsColumns: 2,
            image: false
          }
        ];
      break;
      case 3:
        this.galleryOptions = [
          {
            width: '558px',
            thumbnailsRows: 1,
            thumbnailsColumns: 2,
            thumbnailsRemainingCount: true,
            image: false
          }
        ];
        break;
      case 4:
        this.galleryOptions = [
          {
            width: '558px',
            thumbnailsRows: 2,
            thumbnailsColumns: 2,
            image: false
          }
        ];
        break;

      default:
        this.galleryOptions = [
          {
            width: '558px',
            thumbnailsRows: 2,
            thumbnailsColumns: 2,
            thumbnailsRemainingCount: true,
            image: false
          }
        ];
    }
  }

  mostraGaleria(): boolean {
    return (this.post ? (this.post.arquivos ? this.post.arquivos.length > 1 : false)  : false);
  }
  mostraImagem(): boolean {
    return this.arquivos().length === 1;
  }

  arquivos(): IArquivo[] {
    return this.post ? (this.post.arquivos ? this.post.arquivos : []) : [];
  }

  linkPostUnico(): string {
    if (!this.arquivos()[0] ) return "";
    const oLink = this.arquivos()[0].link;
    if (!oLink) return "";
    if (oLink === undefined) return ""; else return oLink;

  }
}
