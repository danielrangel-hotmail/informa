import { browser, ExpectedConditions as ec /* , protractor, promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  ArquivoComponentsPage,
  /* ArquivoDeleteDialog,
   */ ArquivoUpdatePage
} from './arquivo.page-object';

const expect = chai.expect;

describe('Arquivo e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let arquivoComponentsPage: ArquivoComponentsPage;
  let arquivoUpdatePage: ArquivoUpdatePage;
  /* let arquivoDeleteDialog: ArquivoDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Arquivos', async () => {
    await navBarPage.goToEntity('arquivo');
    arquivoComponentsPage = new ArquivoComponentsPage();
    await browser.wait(ec.visibilityOf(arquivoComponentsPage.title), 5000);
    expect(await arquivoComponentsPage.getTitle()).to.eq('informaApp.arquivo.home.title');
  });

  it('should load create Arquivo page', async () => {
    await arquivoComponentsPage.clickOnCreateButton();
    arquivoUpdatePage = new ArquivoUpdatePage();
    expect(await arquivoUpdatePage.getPageTitle()).to.eq('informaApp.arquivo.home.createOrEditLabel');
    await arquivoUpdatePage.cancel();
  });

  /*  it('should create and save Arquivos', async () => {
        const nbButtonsBeforeCreate = await arquivoComponentsPage.countDeleteButtons();

        await arquivoComponentsPage.clickOnCreateButton();
        await promise.all([
            arquivoUpdatePage.setVersaoInput('5'),
            arquivoUpdatePage.setCriacaoInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            arquivoUpdatePage.setUltimaEdicaoInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            arquivoUpdatePage.setNomeInput('nome'),
            arquivoUpdatePage.setColecaoInput('colecao'),
            arquivoUpdatePage.setTipoInput('tipo'),
            arquivoUpdatePage.setLinkInput('link'),
            arquivoUpdatePage.usuarioSelectLastOption(),
            arquivoUpdatePage.postSelectLastOption(),
            arquivoUpdatePage.mensagemSelectLastOption(),
        ]);
        expect(await arquivoUpdatePage.getVersaoInput()).to.eq('5', 'Expected versao value to be equals to 5');
        expect(await arquivoUpdatePage.getCriacaoInput()).to.contain('2001-01-01T02:30', 'Expected criacao value to be equals to 2000-12-31');
        expect(await arquivoUpdatePage.getUltimaEdicaoInput()).to.contain('2001-01-01T02:30', 'Expected ultimaEdicao value to be equals to 2000-12-31');
        expect(await arquivoUpdatePage.getNomeInput()).to.eq('nome', 'Expected Nome value to be equals to nome');
        expect(await arquivoUpdatePage.getColecaoInput()).to.eq('colecao', 'Expected Colecao value to be equals to colecao');
        expect(await arquivoUpdatePage.getTipoInput()).to.eq('tipo', 'Expected Tipo value to be equals to tipo');
        expect(await arquivoUpdatePage.getLinkInput()).to.eq('link', 'Expected Link value to be equals to link');
        const selectedUploadConfirmado = arquivoUpdatePage.getUploadConfirmadoInput();
        if (await selectedUploadConfirmado.isSelected()) {
            await arquivoUpdatePage.getUploadConfirmadoInput().click();
            expect(await arquivoUpdatePage.getUploadConfirmadoInput().isSelected(), 'Expected uploadConfirmado not to be selected').to.be.false;
        } else {
            await arquivoUpdatePage.getUploadConfirmadoInput().click();
            expect(await arquivoUpdatePage.getUploadConfirmadoInput().isSelected(), 'Expected uploadConfirmado to be selected').to.be.true;
        }
        await arquivoUpdatePage.save();
        expect(await arquivoUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await arquivoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /*  it('should delete last Arquivo', async () => {
        const nbButtonsBeforeDelete = await arquivoComponentsPage.countDeleteButtons();
        await arquivoComponentsPage.clickOnLastDeleteButton();

        arquivoDeleteDialog = new ArquivoDeleteDialog();
        expect(await arquivoDeleteDialog.getDialogTitle())
            .to.eq('informaApp.arquivo.delete.question');
        await arquivoDeleteDialog.clickOnConfirmButton();

        expect(await arquivoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
