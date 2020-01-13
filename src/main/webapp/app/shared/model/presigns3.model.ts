export interface IPreSignS3 {
  method?: string;
  url?: string;
  fields?: any;
}

export class IPreSignS3 implements IPreSignS3 {

  constructor(
    public method?: string,
    public url?: string,
    public fields?: any,
  ) {
  }
}
