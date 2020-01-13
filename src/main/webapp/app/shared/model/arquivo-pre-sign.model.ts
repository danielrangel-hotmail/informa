export interface IArquivoPreSign {
  filename?: string;
  contentType?: string;
}

export class ArquivoPreSign implements IArquivoPreSign {

  constructor(
    filename?: string,
    contentType?: string
  ) {
  }
}
