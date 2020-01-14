import { element, by, ElementFinder } from 'protractor';

export class ArquivoComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-arquivo div table .btn-danger'));
  title = element.all(by.css('jhi-arquivo div h2#page-heading span')).first();

  async clickOnCreateButton(): Promise<void> {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(): Promise<void> {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons(): Promise<number> {
    return this.deleteButtons.count();
  }

  async getTitle(): Promise<string> {
    return this.title.getAttribute('jhiTranslate');
  }
}

export class ArquivoUpdatePage {
  pageTitle = element(by.id('jhi-arquivo-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  versaoInput = element(by.id('field_versao'));
  criacaoInput = element(by.id('field_criacao'));
  ultimaEdicaoInput = element(by.id('field_ultimaEdicao'));
  nomeInput = element(by.id('field_nome'));
  colecaoInput = element(by.id('field_colecao'));
  tipoInput = element(by.id('field_tipo'));
  linkInput = element(by.id('field_link'));
  uploadConfirmadoInput = element(by.id('field_uploadConfirmado'));
  usuarioSelect = element(by.id('field_usuario'));
  postSelect = element(by.id('field_post'));
  mensagemSelect = element(by.id('field_mensagem'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setVersaoInput(versao: string): Promise<void> {
    await this.versaoInput.sendKeys(versao);
  }

  async getVersaoInput(): Promise<string> {
    return await this.versaoInput.getAttribute('value');
  }

  async setCriacaoInput(criacao: string): Promise<void> {
    await this.criacaoInput.sendKeys(criacao);
  }

  async getCriacaoInput(): Promise<string> {
    return await this.criacaoInput.getAttribute('value');
  }

  async setUltimaEdicaoInput(ultimaEdicao: string): Promise<void> {
    await this.ultimaEdicaoInput.sendKeys(ultimaEdicao);
  }

  async getUltimaEdicaoInput(): Promise<string> {
    return await this.ultimaEdicaoInput.getAttribute('value');
  }

  async setNomeInput(nome: string): Promise<void> {
    await this.nomeInput.sendKeys(nome);
  }

  async getNomeInput(): Promise<string> {
    return await this.nomeInput.getAttribute('value');
  }

  async setColecaoInput(colecao: string): Promise<void> {
    await this.colecaoInput.sendKeys(colecao);
  }

  async getColecaoInput(): Promise<string> {
    return await this.colecaoInput.getAttribute('value');
  }

  async setTipoInput(tipo: string): Promise<void> {
    await this.tipoInput.sendKeys(tipo);
  }

  async getTipoInput(): Promise<string> {
    return await this.tipoInput.getAttribute('value');
  }

  async setLinkInput(link: string): Promise<void> {
    await this.linkInput.sendKeys(link);
  }

  async getLinkInput(): Promise<string> {
    return await this.linkInput.getAttribute('value');
  }

  getUploadConfirmadoInput(): ElementFinder {
    return this.uploadConfirmadoInput;
  }

  async usuarioSelectLastOption(): Promise<void> {
    await this.usuarioSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async usuarioSelectOption(option: string): Promise<void> {
    await this.usuarioSelect.sendKeys(option);
  }

  getUsuarioSelect(): ElementFinder {
    return this.usuarioSelect;
  }

  async getUsuarioSelectedOption(): Promise<string> {
    return await this.usuarioSelect.element(by.css('option:checked')).getText();
  }

  async postSelectLastOption(): Promise<void> {
    await this.postSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async postSelectOption(option: string): Promise<void> {
    await this.postSelect.sendKeys(option);
  }

  getPostSelect(): ElementFinder {
    return this.postSelect;
  }

  async getPostSelectedOption(): Promise<string> {
    return await this.postSelect.element(by.css('option:checked')).getText();
  }

  async mensagemSelectLastOption(): Promise<void> {
    await this.mensagemSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async mensagemSelectOption(option: string): Promise<void> {
    await this.mensagemSelect.sendKeys(option);
  }

  getMensagemSelect(): ElementFinder {
    return this.mensagemSelect;
  }

  async getMensagemSelectedOption(): Promise<string> {
    return await this.mensagemSelect.element(by.css('option:checked')).getText();
  }

  async save(): Promise<void> {
    await this.saveButton.click();
  }

  async cancel(): Promise<void> {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class ArquivoDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-arquivo-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-arquivo'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
