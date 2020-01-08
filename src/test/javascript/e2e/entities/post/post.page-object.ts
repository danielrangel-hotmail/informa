import { element, by, ElementFinder } from 'protractor';

export class PostComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-post div table .btn-danger'));
  title = element.all(by.css('jhi-post div h2#page-heading span')).first();

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

export class PostUpdatePage {
  pageTitle = element(by.id('jhi-post-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  versaoInput = element(by.id('field_versao'));
  criacaoInput = element(by.id('field_criacao'));
  ultimaEdicaoInput = element(by.id('field_ultimaEdicao'));
  conteudoInput = element(by.id('field_conteudo'));
  publicacaoInput = element(by.id('field_publicacao'));
  autorSelect = element(by.id('field_autor'));
  grupoSelect = element(by.id('field_grupo'));

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

  async setPublicacaoInput(publicacao: string): Promise<void> {
    await this.publicacaoInput.sendKeys(publicacao);
  }

  async getPublicacaoInput(): Promise<string> {
    return await this.publicacaoInput.getAttribute('value');
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

  async grupoSelectLastOption(): Promise<void> {
    await this.grupoSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async grupoSelectOption(option: string): Promise<void> {
    await this.grupoSelect.sendKeys(option);
  }

  getGrupoSelect(): ElementFinder {
    return this.grupoSelect;
  }

  async getGrupoSelectedOption(): Promise<string> {
    return await this.grupoSelect.element(by.css('option:checked')).getText();
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

export class PostDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-post-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-post'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
