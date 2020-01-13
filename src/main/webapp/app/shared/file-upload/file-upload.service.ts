import { Injectable } from '@angular/core';
import {SERVER_API_URL} from 'app/app.constants';
import { Observable } from 'rxjs';
import { HttpResponse, HttpClient } from '@angular/common/http';
import { IPreSignS3 } from 'app/shared/model/presigns3.model';
import { IArquivoPreSign } from 'app/shared/model/arquivo-pre-sign.model';
import { FileItem } from 'ng2-file-upload';


@Injectable({ providedIn: 'root' })
export class FileUploadService {
  public resourceUrl = SERVER_API_URL + 'api/s3-presign';

  constructor(protected http: HttpClient) {}

  preSign(fileItem: FileItem): Observable<HttpResponse<IPreSignS3>> {
    const arquivoPreSign: IArquivoPreSign = {filename: fileItem.file.name, contentType: fileItem.file.type};
    return this.http
      .post<IPreSignS3>(this.resourceUrl, arquivoPreSign, { observe: 'response' });
  }
}
