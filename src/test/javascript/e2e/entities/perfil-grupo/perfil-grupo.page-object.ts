import { element, by, ElementFinder } from 'protractor';

export class PerfilGrupoComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-perfil-grupo div table .btn-danger'));
  title = element.all(by.css('jhi-perfil-grupo div h2#page-heading span')).first();

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

export class PerfilGrupoUpdatePage {
  pageTitle = element(by.id('jhi-perfil-grupo-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  criacaoInput = element(by.id('field_criacao'));
  ultimaEdicaoInput = element(by.id('field_ultimaEdicao'));
  versaoInput = element(by.id('field_versao'));
  favoritoInput = element(by.id('field_favorito'));
  notificaInput = element(by.id('field_notifica'));
  perfilSelect = element(by.id('field_perfil'));
  grupoSelect = element(by.id('field_grupo'));

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

  getFavoritoInput(): ElementFinder {
    return this.favoritoInput;
  }
  getNotificaInput(): ElementFinder {
    return this.notificaInput;
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

export class PerfilGrupoDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-perfilGrupo-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-perfilGrupo'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
