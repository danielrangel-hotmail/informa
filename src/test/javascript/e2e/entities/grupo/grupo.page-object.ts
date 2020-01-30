import { element, by, ElementFinder } from 'protractor';

export class GrupoComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-grupo div table .btn-danger'));
  title = element.all(by.css('jhi-grupo div h2#page-heading span')).first();

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

export class GrupoUpdatePage {
  pageTitle = element(by.id('jhi-grupo-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  versaoInput = element(by.id('field_versao'));
  criacaoInput = element(by.id('field_criacao'));
  ultimaEdicaoInput = element(by.id('field_ultimaEdicao'));
  nomeInput = element(by.id('field_nome'));
  descricaoInput = element(by.id('field_descricao'));
  formalInput = element(by.id('field_formal'));
  opcionalInput = element(by.id('field_opcional'));
  logoInput = element(by.id('file_logo'));
  cabecalhoSuperiorCorInput = element(by.id('field_cabecalhoSuperiorCor'));
  cabecalhoInferiorCorInput = element(by.id('field_cabecalhoInferiorCor'));
  logoFundoCorInput = element(by.id('field_logoFundoCor'));
  topicosSelect = element(by.id('field_topicos'));

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

  async setDescricaoInput(descricao: string): Promise<void> {
    await this.descricaoInput.sendKeys(descricao);
  }

  async getDescricaoInput(): Promise<string> {
    return await this.descricaoInput.getAttribute('value');
  }

  getFormalInput(): ElementFinder {
    return this.formalInput;
  }
  getOpcionalInput(): ElementFinder {
    return this.opcionalInput;
  }
  async setLogoInput(logo: string): Promise<void> {
    await this.logoInput.sendKeys(logo);
  }

  async getLogoInput(): Promise<string> {
    return await this.logoInput.getAttribute('value');
  }

  async setCabecalhoSuperiorCorInput(cabecalhoSuperiorCor: string): Promise<void> {
    await this.cabecalhoSuperiorCorInput.sendKeys(cabecalhoSuperiorCor);
  }

  async getCabecalhoSuperiorCorInput(): Promise<string> {
    return await this.cabecalhoSuperiorCorInput.getAttribute('value');
  }

  async setCabecalhoInferiorCorInput(cabecalhoInferiorCor: string): Promise<void> {
    await this.cabecalhoInferiorCorInput.sendKeys(cabecalhoInferiorCor);
  }

  async getCabecalhoInferiorCorInput(): Promise<string> {
    return await this.cabecalhoInferiorCorInput.getAttribute('value');
  }

  async setLogoFundoCorInput(logoFundoCor: string): Promise<void> {
    await this.logoFundoCorInput.sendKeys(logoFundoCor);
  }

  async getLogoFundoCorInput(): Promise<string> {
    return await this.logoFundoCorInput.getAttribute('value');
  }

  async topicosSelectLastOption(): Promise<void> {
    await this.topicosSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async topicosSelectOption(option: string): Promise<void> {
    await this.topicosSelect.sendKeys(option);
  }

  getTopicosSelect(): ElementFinder {
    return this.topicosSelect;
  }

  async getTopicosSelectedOption(): Promise<string> {
    return await this.topicosSelect.element(by.css('option:checked')).getText();
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

export class GrupoDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-grupo-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-grupo'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
