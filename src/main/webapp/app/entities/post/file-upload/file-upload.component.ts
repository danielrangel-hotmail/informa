import { Component, OnInit, Input } from '@angular/core';
import { FileUploader, FileItem } from 'ng2-file-upload';
import { HttpResponse } from '@angular/common/http';
import {ArquivoService} from 'app/entities/arquivo/arquivo.service';
import {Arquivo} from 'app/shared/model/arquivo.model';
import {IPost} from 'app/shared/model/post.model';

// const URL = '/api/';
const URL = 'https://evening-anchorage-3159.herokuapp.com/api/';

@Component({
  selector: 'jhi-file-upload',
  templateUrl: './file-upload.component.html'
})
export class FileUploadComponent implements OnInit{

  @Input() post?: IPost;
  uploader:FileUploader;
  hasBaseDropZoneOver:boolean;
  response:string;

  constructor (protected arquivoService: ArquivoService ){

    const ourFormatDataFunction = async (item: any) => {
      return new Promise((resolve, reject) => {
        resolve({
          name: item._file.name,
          length: item._file.size,
          contentType: item._file.type,
          date: new Date()
        });
      });
    }
    this.uploader = new FileUploader({
      url: URL,
      disableMultipart: true,
      formatDataFunctionIsAsync: true,
      formatDataFunction: ourFormatDataFunction
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
          const url = result.body ? result.body.s3PresignedURL : null;
          if (url != null) item.url = url;
          item.method = 'PUT';
          item.headers =  [{name: 'Content-Type', value: item.file.type}];
          item.withCredentials = false;
          item.upload();
        }
      )
    });
  }

}
