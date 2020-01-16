import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { IPost } from 'app/shared/model/post.model';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import {IArquivo} from 'app/shared/model/arquivo.model';
import { NgxGalleryImage, NgxGalleryOptions } from 'ngx-gallery';
import {AMAZON_S3_BUCKET_URL} from 'app/entities/arquivo/arquivo.constants';

@Component({
  selector: 'jhi-post-image',
  templateUrl: './post-image.component.html',
  styleUrls: ['./post-image.component.scss']
})
export class PostImageComponent implements OnInit {
  private post$?: Observable<IPost>;
  galleryOptions: NgxGalleryOptions[];
  bucketUrl = AMAZON_S3_BUCKET_URL;
  galleryImagesParaPost?: NgxGalleryImage[];
  arquivosDoPost?: IArquivo[];
  fazendoUpload = false;

  constructor(protected activatedRoute: ActivatedRoute) {
    this.galleryOptions = [
      {
        width: '600px',
        height: '400px',
        thumbnailsColumns: 4,
        thumbnailsRows: 1,
        thumbnailActions: [{icon: 'fa fa-trash', onClick: this.deleteImage.bind(this), titleText: 'delete'}]
      }
    ];
  }

  deleteImage(image: any, i: any): void {
    // eslint-disable-next-line no-console
    console.log(this.arquivosDoPost ? this.arquivosDoPost[i] : "nada");
  }

  ngOnInit(): void {
    const routerParent = this.activatedRoute.parent;
    if (routerParent != null) {
      this.post$ = routerParent.data
        .pipe(map((data) => {return data.post}));
      this.post$.subscribe((post: IPost) => {
        this.arquivosDoPost = post.arquivos;
        this.fazendoUpload = post.arquivos ? post.arquivos.length === 0 : true;
      })
    }
  }

  galleryImages(post: IPost): NgxGalleryImage[] {
    if (!post.arquivos) return [];
    if (this.galleryImagesParaPost != null) return this.galleryImagesParaPost;

    this.galleryImagesParaPost = post.arquivos.map(  (arquivo) => (
      {
        small: this.bucketUrl+arquivo.link,
        medium: this.bucketUrl+arquivo.link,
        big: this.bucketUrl+arquivo.link,
      }
    ));
    return this.galleryImagesParaPost;
  }

  mostraGaleria(post: IPost): boolean {
    return  !this.fazendoUpload;
  }

  terminouUpload(): void {
    this.fazendoUpload = false;
  }

  maisImagens(): void {
    this.fazendoUpload = true;
  }
}
