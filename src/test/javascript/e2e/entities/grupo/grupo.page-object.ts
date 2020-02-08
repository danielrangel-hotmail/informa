import { browser, by, element, ElementFinder } from 'protractor';
import { addElementNgSelect } from '../../util/util';

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

export class GrupoCabecalhoPage {
  testa1 = element(by.className("testa1"));
  testa2 = element(by.className("testa2"));
  nome = element(by.className("nome"));
  descricao = element(by.id("descricao"));
  logo = element(by.tagName("img"));

  async getCorTesta1(): Promise<string> {
    return this.testa1.getCssValue("background");
  }

  async getCorTesta2(): Promise<string> {
    return this.testa2.getCssValue("background");
  }

  async getNome(): Promise<string> {
    return this.nome.getText();
  }

  async getDescricao(): Promise<string> {
    return this.descricao.getText();
  }

  async getLogoSource(): Promise<string> {
    return this.logo.getAttribute("src");
  }
}

export class GrupoUpdatePage {
  editForm = element(by.name('editForm'));
  pageTitle = this.editForm.element(by.tagName('h4'));
  saveButton = this.editForm.element(by.id('save-entity'));
  cancelButton = this.editForm.element(by.id('cancel-save'));
  nomeInput = this.editForm.element(by.id('field_nome'));
  descricaoInput = this.editForm.element(by.id('field_descricao'));
  formalInput = this.editForm.element(by.id('field_formal'));
  opcionalInput = this.editForm.element(by.id('field_opcional'));
  logoInput = this.editForm.element(by.id('file_logo'));
  cabecalhoSuperiorCorInput = this.editForm.element(by.id('field_cabecalhoSuperiorCor'));
  cabecalhoInferiorCorInput = this.editForm.element(by.id('field_cabecalhoInferiorCor'));
  logoFundoCorInput = element(by.id('field_logoFundoCor'));
  topicosSelect = this.editForm.element(by.id('field_topicos'));
  moderadores = this.editForm.element(by.id('field_moderadores'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getText();
  }

  async getModeradores(): Promise<string[]> {
    return this.moderadores.all(by.className('ng-value-label'))
      .map<string>((label: any) => label.getText());
  }

  async addModerador(moderador: string): Promise<void> {
      await addElementNgSelect(this.moderadores, moderador);
  }

  async addTopico(topico: string): Promise<void> {
    await addElementNgSelect(this.topicosSelect, topico);
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
    await this.cabecalhoSuperiorCorInput.click();
    const input = this.cabecalhoSuperiorCorInput
      .$('.hex-text')
      .element(by.tagName('input'));
    await input.clear();
    await input.sendKeys(cabecalhoSuperiorCor);
  }

  async getCabecalhoSuperiorCorInput(): Promise<string> {
    return await this.cabecalhoSuperiorCorInput.element(by.tagName("input")).getCssValue("background");
  }

  async setCabecalhoInferiorCorInput(cabecalhoInferiorCor: string): Promise<void> {
    await this.cabecalhoInferiorCorInput.click();
    const input = this.cabecalhoInferiorCorInput
      .$('.hex-text')
      .element(by.tagName('input'));
    await input.clear();
    await input.sendKeys(cabecalhoInferiorCor);
    await browser.sleep(1000);
  }

  async getCabecalhoInferiorCorInput(): Promise<string> {
    return await this.cabecalhoInferiorCorInput.element(by.tagName("input")).getCssValue("background");
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
