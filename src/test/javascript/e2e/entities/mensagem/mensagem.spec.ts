import { browser, ExpectedConditions as ec /* , protractor, promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  MensagemComponentsPage,
  /* MensagemDeleteDialog,
   */ MensagemUpdatePage
} from './mensagem.page-object';

const expect = chai.expect;

describe('Mensagem e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let mensagemComponentsPage: MensagemComponentsPage;
  let mensagemUpdatePage: MensagemUpdatePage;
  /* let mensagemDeleteDialog: MensagemDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Mensagems', async () => {
    await navBarPage.goToEntity('mensagem');
    mensagemComponentsPage = new MensagemComponentsPage();
    await browser.wait(ec.visibilityOf(mensagemComponentsPage.title), 5000);
    expect(await mensagemComponentsPage.getTitle()).to.eq('informaApp.mensagem.home.title');
  });

  it('should load create Mensagem page', async () => {
    await mensagemComponentsPage.clickOnCreateButton();
    mensagemUpdatePage = new MensagemUpdatePage();
    expect(await mensagemUpdatePage.getPageTitle()).to.eq('informaApp.mensagem.home.createOrEditLabel');
    await mensagemUpdatePage.cancel();
  });

  /*  it('should create and save Mensagems', async () => {
        const nbButtonsBeforeCreate = await mensagemComponentsPage.countDeleteButtons();

        await mensagemComponentsPage.clickOnCreateButton();
        await promise.all([
            mensagemUpdatePage.setVersaoInput('5'),
            mensagemUpdatePage.setCriacaoInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            mensagemUpdatePage.setUltimaEdicaoInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            mensagemUpdatePage.setConteudoInput('conteudo'),
            mensagemUpdatePage.autorSelectLastOption(),
            mensagemUpdatePage.postSelectLastOption(),
            mensagemUpdatePage.conversaSelectLastOption(),
        ]);
        expect(await mensagemUpdatePage.getVersaoInput()).to.eq('5', 'Expected versao value to be equals to 5');
        expect(await mensagemUpdatePage.getCriacaoInput()).to.contain('2001-01-01T02:30', 'Expected criacao value to be equals to 2000-12-31');
        expect(await mensagemUpdatePage.getUltimaEdicaoInput()).to.contain('2001-01-01T02:30', 'Expected ultimaEdicao value to be equals to 2000-12-31');
        expect(await mensagemUpdatePage.getConteudoInput()).to.eq('conteudo', 'Expected Conteudo value to be equals to conteudo');
        const selectedTemConversa = mensagemUpdatePage.getTemConversaInput();
        if (await selectedTemConversa.isSelected()) {
            await mensagemUpdatePage.getTemConversaInput().click();
            expect(await mensagemUpdatePage.getTemConversaInput().isSelected(), 'Expected temConversa not to be selected').to.be.false;
        } else {
            await mensagemUpdatePage.getTemConversaInput().click();
            expect(await mensagemUpdatePage.getTemConversaInput().isSelected(), 'Expected temConversa to be selected').to.be.true;
        }
        await mensagemUpdatePage.save();
        expect(await mensagemUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await mensagemComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /*  it('should delete last Mensagem', async () => {
        const nbButtonsBeforeDelete = await mensagemComponentsPage.countDeleteButtons();
        await mensagemComponentsPage.clickOnLastDeleteButton();

        mensagemDeleteDialog = new MensagemDeleteDialog();
        expect(await mensagemDeleteDialog.getDialogTitle())
            .to.eq('informaApp.mensagem.delete.question');
        await mensagemDeleteDialog.clickOnConfirmButton();

        expect(await mensagemComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
