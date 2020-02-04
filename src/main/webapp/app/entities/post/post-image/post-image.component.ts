import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { IPost } from 'app/shared/model/post.interface';
import { combineLatest, forkJoin, Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import {IArquivo} from 'app/shared/model/arquivo.model';
import { NgxGalleryImage, NgxGalleryOptions } from 'ngx-gallery';
import { DeviceDetectorService } from 'ngx-device-detector';
import { ArquivoService } from 'app/entities/arquivo/arquivo.service';


@Component({
  selector: 'jhi-post-image',
  templateUrl: './post-image.component.html',
  styleUrls: ['./post-image.component.scss']
})
export class PostImageComponent implements OnInit {
  post?: IPost;
  bucketUrl?: string;
  galleryOptions: NgxGalleryOptions[];
  galleryImagesParaPost?: NgxGalleryImage[];
  fazendoUpload = false;

  constructor(
    protected activatedRoute: ActivatedRoute,
    protected arquivoService: ArquivoService,
    public deviceService: DeviceDetectorService) {
    this.galleryOptions = [
      {
        width: '100%',
        height: '400px',
        thumbnailsColumns: 4,
        thumbnailsRows: 1,
        thumbnailActions: [{icon: 'fa fa-trash', onClick: this.deleteImage.bind(this), titleText: 'delete'}]
      }
    ];
  }

  deleteImage(image: any, i: any): void {
    // eslint-disable-next-line no-console
    // console.log(this.arquivosDoPost ? this.arquivosDoPost[i] : "nada");
  }

  ngOnInit(): void {
    const routerParent = this.activatedRoute.parent;
    if (routerParent != null) {
      const post$ = routerParent.data.pipe(map((data) => {return data.post}));
      combineLatest(
        [
          this.arquivoService.bucketUrl$,
          post$
        ]
      ).subscribe(([ bucketUrl, post]) => {
        this.bucketUrl = bucketUrl;
        this.post = post;
        this.fazendoUpload = post.arquivos ? post.arquivos.length === 0 : true;
      });
    }
  }

  galleryImages(post: IPost): NgxGalleryImage[] {
    if (!post.arquivos) return [];
    if (this.galleryImagesParaPost != null) return this.galleryImagesParaPost;

    this.galleryImagesParaPost = post.arquivos.map(  (arquivo) => (
      {
        small: this.bucketUrl! + arquivo.link,
        medium: this.bucketUrl! + arquivo.link,
        big: this.bucketUrl! + arquivo.link,
      }
    ));
    return this.galleryImagesParaPost;
  }

  mostraMaisImagens(): boolean {
    return (!this.deviceService.isMobile()) && (!this.fazendoUpload);
  }

  mostraFileUpload(): boolean {
    return (this.deviceService.isMobile()) || (this.fazendoUpload);
  }

  mostraGaleria(): boolean {
    return  (this.deviceService.isMobile()) || (!this.fazendoUpload);
  }

  terminouUpload(): void {
    this.fazendoUpload = false;
  }

  maisImagens(): void {
    this.fazendoUpload = true;
  }
}
