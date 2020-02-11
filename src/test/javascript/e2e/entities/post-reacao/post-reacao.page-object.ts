import { element, by, ElementFinder } from 'protractor';

export class PostReacaoComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-post-reacao div table .btn-danger'));
  title = element.all(by.css('jhi-post-reacao div h2#page-heading span')).first();

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

export class PostReacaoUpdatePage {
  pageTitle = element(by.id('jhi-post-reacao-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  criacaoInput = element(by.id('field_criacao'));
  ultimaEdicaoInput = element(by.id('field_ultimaEdicao'));
  versaoInput = element(by.id('field_versao'));
  reacaoInput = element(by.id('field_reacao'));
  perfilSelect = element(by.id('field_perfil'));
  postSelect = element(by.id('field_post'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
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

  async setVersaoInput(versao: string): Promise<void> {
    await this.versaoInput.sendKeys(versao);
  }

  async getVersaoInput(): Promise<string> {
    return await this.versaoInput.getAttribute('value');
  }

  async setReacaoInput(reacao: string): Promise<void> {
    await this.reacaoInput.sendKeys(reacao);
  }

  async getReacaoInput(): Promise<string> {
    return await this.reacaoInput.getAttribute('value');
  }

  async perfilSelectLastOption(): Promise<void> {
    await this.perfilSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async perfilSelectOption(option: string): Promise<void> {
    await this.perfilSelect.sendKeys(option);
  }

  getPerfilSelect(): ElementFinder {
    return this.perfilSelect;
  }

  async getPerfilSelectedOption(): Promise<string> {
    return await this.perfilSelect.element(by.css('option:checked')).getText();
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

export class PostReacaoDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-postReacao-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-postReacao'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
