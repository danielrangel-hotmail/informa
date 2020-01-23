import { element, by, ElementFinder } from 'protractor';

export class PushSubscriptionComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-push-subscription div table .btn-danger'));
  title = element.all(by.css('jhi-push-subscription div h2#page-heading span')).first();

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

export class PushSubscriptionUpdatePage {
  pageTitle = element(by.id('jhi-push-subscription-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  versaoInput = element(by.id('field_versao'));
  criacaoInput = element(by.id('field_criacao'));
  endpointInput = element(by.id('field_endpoint'));
  keyInput = element(by.id('field_key'));
  authInput = element(by.id('field_auth'));
  perfilSelect = element(by.id('field_perfil'));

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

  async setEndpointInput(endpoint: string): Promise<void> {
    await this.endpointInput.sendKeys(endpoint);
  }

  async getEndpointInput(): Promise<string> {
    return await this.endpointInput.getAttribute('value');
  }

  async setKeyInput(key: string): Promise<void> {
    await this.keyInput.sendKeys(key);
  }

  async getKeyInput(): Promise<string> {
    return await this.keyInput.getAttribute('value');
  }

  async setAuthInput(auth: string): Promise<void> {
    await this.authInput.sendKeys(auth);
  }

  async getAuthInput(): Promise<string> {
    return await this.authInput.getAttribute('value');
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

export class PushSubscriptionDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-pushSubscription-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-pushSubscription'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
