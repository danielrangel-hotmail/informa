import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { GrupoComponentsPage, GrupoDeleteDialog, GrupoUpdatePage } from './grupo.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('Grupo e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let grupoComponentsPage: GrupoComponentsPage;
  let grupoUpdatePage: GrupoUpdatePage;
  let grupoDeleteDialog: GrupoDeleteDialog;
  const fileNameToUpload = 'logo-jhipster.png';
  const fileToUpload = '../../../../../../src/main/webapp/content/images/' + fileNameToUpload;
  const absolutePath = path.resolve(__dirname, fileToUpload);

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Grupos', async () => {
    await navBarPage.goToEntity('grupo');
    grupoComponentsPage = new GrupoComponentsPage();
    await browser.wait(ec.visibilityOf(grupoComponentsPage.title), 5000);
    expect(await grupoComponentsPage.getTitle()).to.eq('informaApp.grupo.home.title');
  });

  it('should load create Grupo page', async () => {
    await grupoComponentsPage.clickOnCreateButton();
    grupoUpdatePage = new GrupoUpdatePage();
    expect(await grupoUpdatePage.getPageTitle()).to.eq('informaApp.grupo.home.createOrEditLabel');
    await grupoUpdatePage.cancel();
  });

  it('should create and save Grupos', async () => {
    const nbButtonsBeforeCreate = await grupoComponentsPage.countDeleteButtons();

    await grupoComponentsPage.clickOnCreateButton();
    await promise.all([
      grupoUpdatePage.setVersaoInput('5'),
      grupoUpdatePage.setCriacaoInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      grupoUpdatePage.setUltimaEdicaoInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      grupoUpdatePage.setNomeInput('nome'),
      grupoUpdatePage.setDescricaoInput('descricao'),
      grupoUpdatePage.setLogoInput(absolutePath),
      grupoUpdatePage.setCabecalhoSuperiorCorInput('cabecalhoSuperiorCor'),
      grupoUpdatePage.setCabecalhoInferiorCorInput('cabecalhoInferiorCor'),
      grupoUpdatePage.setLogoFundoCorInput('logoFundoCor')
      // grupoUpdatePage.topicosSelectLastOption(),
    ]);
    expect(await grupoUpdatePage.getVersaoInput()).to.eq('5', 'Expected versao value to be equals to 5');
    expect(await grupoUpdatePage.getCriacaoInput()).to.contain('2001-01-01T02:30', 'Expected criacao value to be equals to 2000-12-31');
    expect(await grupoUpdatePage.getUltimaEdicaoInput()).to.contain(
      '2001-01-01T02:30',
      'Expected ultimaEdicao value to be equals to 2000-12-31'
    );
    expect(await grupoUpdatePage.getNomeInput()).to.eq('nome', 'Expected Nome value to be equals to nome');
    expect(await grupoUpdatePage.getDescricaoInput()).to.eq('descricao', 'Expected Descricao value to be equals to descricao');
    const selectedFormal = grupoUpdatePage.getFormalInput();
    if (await selectedFormal.isSelected()) {
      await grupoUpdatePage.getFormalInput().click();
      expect(await grupoUpdatePage.getFormalInput().isSelected(), 'Expected formal not to be selected').to.be.false;
    } else {
      await grupoUpdatePage.getFormalInput().click();
      expect(await grupoUpdatePage.getFormalInput().isSelected(), 'Expected formal to be selected').to.be.true;
    }
    const selectedOpcional = grupoUpdatePage.getOpcionalInput();
    if (await selectedOpcional.isSelected()) {
      await grupoUpdatePage.getOpcionalInput().click();
      expect(await grupoUpdatePage.getOpcionalInput().isSelected(), 'Expected opcional not to be selected').to.be.false;
    } else {
      await grupoUpdatePage.getOpcionalInput().click();
      expect(await grupoUpdatePage.getOpcionalInput().isSelected(), 'Expected opcional to be selected').to.be.true;
    }
    expect(await grupoUpdatePage.getLogoInput()).to.endsWith(fileNameToUpload, 'Expected Logo value to be end with ' + fileNameToUpload);
    expect(await grupoUpdatePage.getCabecalhoSuperiorCorInput()).to.eq(
      'cabecalhoSuperiorCor',
      'Expected CabecalhoSuperiorCor value to be equals to cabecalhoSuperiorCor'
    );
    expect(await grupoUpdatePage.getCabecalhoInferiorCorInput()).to.eq(
      'cabecalhoInferiorCor',
      'Expected CabecalhoInferiorCor value to be equals to cabecalhoInferiorCor'
    );
    expect(await grupoUpdatePage.getLogoFundoCorInput()).to.eq('logoFundoCor', 'Expected LogoFundoCor value to be equals to logoFundoCor');
    await grupoUpdatePage.save();
    expect(await grupoUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await grupoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Grupo', async () => {
    const nbButtonsBeforeDelete = await grupoComponentsPage.countDeleteButtons();
    await grupoComponentsPage.clickOnLastDeleteButton();

    grupoDeleteDialog = new GrupoDeleteDialog();
    expect(await grupoDeleteDialog.getDialogTitle()).to.eq('informaApp.grupo.delete.question');
    await grupoDeleteDialog.clickOnConfirmButton();

    expect(await grupoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
