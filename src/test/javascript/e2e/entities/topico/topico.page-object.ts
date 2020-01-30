import { element, by, ElementFinder } from 'protractor';

export class TopicoComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-topico div table .btn-danger'));
  title = element.all(by.css('jhi-topico div h2#page-heading span')).first();

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

export class TopicoUpdatePage {
  pageTitle = element(by.id('jhi-topico-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  versaoInput = element(by.id('field_versao'));
  nomeInput = element(by.id('field_nome'));
  autorSelect = element(by.id('field_autor'));
  substitutoSelect = element(by.id('field_substituto'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setVersaoInput(versao: string): Promise<void> {
    await this.versaoInput.sendKeys(versao);
  }

  async getVersaoInput(): Promise<string> {
    return await this.versaoInput.getAttribute('value');
  }

  async setNomeInput(nome: string): Promise<void> {
    await this.nomeInput.sendKeys(nome);
  }

  async getNomeInput(): Promise<string> {
    return await this.nomeInput.getAttribute('value');
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

  async substitutoSelectLastOption(): Promise<void> {
    await this.substitutoSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async substitutoSelectOption(option: string): Promise<void> {
    await this.substitutoSelect.sendKeys(option);
  }

  getSubstitutoSelect(): ElementFinder {
    return this.substitutoSelect;
  }

  async getSubstitutoSelectedOption(): Promise<string> {
    return await this.substitutoSelect.element(by.css('option:checked')).getText();
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

export class TopicoDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-topico-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-topico'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
