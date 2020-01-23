import { element, by, ElementFinder } from 'protractor';

export class PerfilUsuarioComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-perfil-usuario div table .btn-danger'));
  title = element.all(by.css('jhi-perfil-usuario div h2#page-heading span')).first();

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

export class PerfilUsuarioUpdatePage {
  pageTitle = element(by.id('jhi-perfil-usuario-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  criacaoInput = element(by.id('field_criacao'));
  ultimaEdicaoInput = element(by.id('field_ultimaEdicao'));
  versaoInput = element(by.id('field_versao'));
  entradaNaEmpresaInput = element(by.id('field_entradaNaEmpresa'));
  saidaDaEmpresaInput = element(by.id('field_saidaDaEmpresa'));
  nascimentoInput = element(by.id('field_nascimento'));
  skypeInput = element(by.id('field_skype'));
  usuarioSelect = element(by.id('field_usuario'));

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

  async setEntradaNaEmpresaInput(entradaNaEmpresa: string): Promise<void> {
    await this.entradaNaEmpresaInput.sendKeys(entradaNaEmpresa);
  }

  async getEntradaNaEmpresaInput(): Promise<string> {
    return await this.entradaNaEmpresaInput.getAttribute('value');
  }

  async setSaidaDaEmpresaInput(saidaDaEmpresa: string): Promise<void> {
    await this.saidaDaEmpresaInput.sendKeys(saidaDaEmpresa);
  }

  async getSaidaDaEmpresaInput(): Promise<string> {
    return await this.saidaDaEmpresaInput.getAttribute('value');
  }

  async setNascimentoInput(nascimento: string): Promise<void> {
    await this.nascimentoInput.sendKeys(nascimento);
  }

  async getNascimentoInput(): Promise<string> {
    return await this.nascimentoInput.getAttribute('value');
  }

  async setSkypeInput(skype: string): Promise<void> {
    await this.skypeInput.sendKeys(skype);
  }

  async getSkypeInput(): Promise<string> {
    return await this.skypeInput.getAttribute('value');
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

export class PerfilUsuarioDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-perfilUsuario-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-perfilUsuario'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
