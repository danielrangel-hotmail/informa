import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FileUploader, FileItem } from 'ng2-file-upload';
import { HttpResponse } from '@angular/common/http';
import {ArquivoService} from 'app/entities/arquivo/arquivo.service';
import {Arquivo} from '../../../shared/model/arquivo.model';
import {IPost} from '../../../shared/model/post.interface';
import { DeviceDetectorService } from 'ngx-device-detector';

const URL = 'https://nao_usado';

@Component({
  selector: 'jhi-file-upload',
  templateUrl: './file-upload.component.html'
})
export class FileUploadComponent implements OnInit{

  @Input() post?: IPost;
  @Output() uploadFinalizado = new EventEmitter<string>();
  uploader:FileUploader;
  hasBaseDropZoneOver:boolean;
  response:string;

  constructor (
    protected arquivoService: ArquivoService,
    public deviceService: DeviceDetectorService ){

    this.uploader = new FileUploader({
      url: URL,
      disableMultipart: true,
    });
    this.hasBaseDropZoneOver = false;
    this.response = '';
    this.uploader.response.subscribe( (res: any) => this.response = res );
  }

  public fileOverBase(e:any):void {
    this.hasBaseDropZoneOver = e;
  }

  ngOnInit(): void {
    if (!this.post) {
      throw new TypeError("'Post é obrigatório");
    }
    this.uploader.onAfterAddingFile = ((item: FileItem) => {
      const arquivo = new Arquivo();
      arquivo.nome = item.file.name;
      arquivo.tipo = item.file.type;
      if (!this.post) {
        throw new TypeError("'Post é obrigatório");
      }
      if (!this.post.id) {
        throw new TypeError("'Post com id é obrigatório");
      }
      arquivo.postId = this.post.id;
      this.arquivoService.create(arquivo).subscribe(
        (result: HttpResponse<Arquivo>) => {
          if (!result.body) {
            throw new TypeError("Deveria ter retornado um arquivo");
          }
          if (!this.post) {
            throw new TypeError("'Post é obrigatório");
          }
          if (!this.post.arquivos) {
            this.post.arquivos = [ result.body ];
          } else {
            this.post.arquivos = [ ...this.post.arquivos, result.body];
          }
          const url = result.body ? result.body.s3PresignedURL : null;
          if (url != null) item.url = url;
          item.method = 'PUT';
          item.headers =  [
            {name: 'Content-Type', value: item.file.type},
          ];
          item.withCredentials = false;
          item.upload();
        }
      )
    });
    this.uploader.onCompleteAll = () => {
      this.uploadFinalizado.emit("cabou!");
    }
  }

}
