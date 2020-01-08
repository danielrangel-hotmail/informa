import { element, by, ElementFinder } from 'protractor';

export class MensagemComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-mensagem div table .btn-danger'));
  title = element.all(by.css('jhi-mensagem div h2#page-heading span')).first();

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

export class MensagemUpdatePage {
  pageTitle = element(by.id('jhi-mensagem-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  versaoInput = element(by.id('field_versao'));
  criacaoInput = element(by.id('field_criacao'));
  ultimaEdicaoInput = element(by.id('field_ultimaEdicao'));
  conteudoInput = element(by.id('field_conteudo'));
  temConversaInput = element(by.id('field_temConversa'));
  autorSelect = element(by.id('field_autor'));
  postSelect = element(by.id('field_post'));
  conversaSelect = element(by.id('field_conversa'));

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

  async setConteudoInput(conteudo: string): Promise<void> {
    await this.conteudoInput.sendKeys(conteudo);
  }

  async getConteudoInput(): Promise<string> {
    return await this.conteudoInput.getAttribute('value');
  }

  getTemConversaInput(): ElementFinder {
    return this.temConversaInput;
  }

  async autorSelectLastOption(): Promise<void> {
    await this.autorSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async autorSelectOption(option: string): Promise<void> {
    await this.autorSelect.sendKeys(option);
  }

  getAutorSelect(): ElementFinder {
    return this.autorSelect;
  }

  async getAutorSelectedOption(): Promise<string> {
    return await this.autorSelect.element(by.css('option:checked')).getText();
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

  async conversaSelectLastOption(): Promise<void> {
    await this.conversaSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async conversaSelectOption(option: string): Promise<void> {
    await this.conversaSelect.sendKeys(option);
  }

  getConversaSelect(): ElementFinder {
    return this.conversaSelect;
  }

  async getConversaSelectedOption(): Promise<string> {
    return await this.conversaSelect.element(by.css('option:checked')).getText();
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

export class MensagemDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-mensagem-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-mensagem'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
