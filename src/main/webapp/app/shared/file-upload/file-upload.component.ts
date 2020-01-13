import { Component, OnInit } from '@angular/core';
import { FileUploader, FileItem } from 'ng2-file-upload';
import {GrupoService} from 'app/entities/grupo/grupo.service';
import {FileUploadService} from 'app/shared/file-upload/file-upload.service';
import {IPreSignS3} from 'app/shared/model/presigns3.model';
import { HttpResponse } from '@angular/common/http';

// const URL = '/api/';
const URL = 'https://evening-anchorage-3159.herokuapp.com/api/';

@Component({
  selector: 'jhi-file-upload',
  templateUrl: './file-upload.component.html'
})
export class FileUploadComponent implements OnInit{

  uploader:FileUploader;
  hasBaseDropZoneOver:boolean;
  response:string;

  constructor (protected fileUploadService: FileUploadService){

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
    this.uploader.onAfterAddingFile = ((item: FileItem) => {
      this.fileUploadService.preSign(item).subscribe(
        (result: HttpResponse<IPreSignS3>) => {
          const url = result.body ? result.body.url : null;
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
