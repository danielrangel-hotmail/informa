import { browser, ExpectedConditions as ec /* , protractor, promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  LinkExternoComponentsPage,
  /* LinkExternoDeleteDialog,
   */ LinkExternoUpdatePage
} from './link-externo.page-object';

const expect = chai.expect;

describe('LinkExterno e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let linkExternoComponentsPage: LinkExternoComponentsPage;
  let linkExternoUpdatePage: LinkExternoUpdatePage;
  /* let linkExternoDeleteDialog: LinkExternoDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load LinkExternos', async () => {
    await navBarPage.goToEntity('link-externo');
    linkExternoComponentsPage = new LinkExternoComponentsPage();
    await browser.wait(ec.visibilityOf(linkExternoComponentsPage.title), 5000);
    expect(await linkExternoComponentsPage.getTitle()).to.eq('informaApp.linkExterno.home.title');
  });

  it('should load create LinkExterno page', async () => {
    await linkExternoComponentsPage.clickOnCreateButton();
    linkExternoUpdatePage = new LinkExternoUpdatePage();
    expect(await linkExternoUpdatePage.getPageTitle()).to.eq('informaApp.linkExterno.home.createOrEditLabel');
    await linkExternoUpdatePage.cancel();
  });

  /*  it('should create and save LinkExternos', async () => {
        const nbButtonsBeforeCreate = await linkExternoComponentsPage.countDeleteButtons();

        await linkExternoComponentsPage.clickOnCreateButton();
        await promise.all([
            linkExternoUpdatePage.setVersaoInput('5'),
            linkExternoUpdatePage.setCriacaoInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            linkExternoUpdatePage.setUltimaEdicaoInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            linkExternoUpdatePage.tipoSelectLastOption(),
            linkExternoUpdatePage.setLinkInput('link'),
            linkExternoUpdatePage.usuarioSelectLastOption(),
            linkExternoUpdatePage.postSelectLastOption(),
            linkExternoUpdatePage.mensagemSelectLastOption(),
        ]);
        expect(await linkExternoUpdatePage.getVersaoInput()).to.eq('5', 'Expected versao value to be equals to 5');
        expect(await linkExternoUpdatePage.getCriacaoInput()).to.contain('2001-01-01T02:30', 'Expected criacao value to be equals to 2000-12-31');
        expect(await linkExternoUpdatePage.getUltimaEdicaoInput()).to.contain('2001-01-01T02:30', 'Expected ultimaEdicao value to be equals to 2000-12-31');
        expect(await linkExternoUpdatePage.getLinkInput()).to.eq('link', 'Expected Link value to be equals to link');
        await linkExternoUpdatePage.save();
        expect(await linkExternoUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await linkExternoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /*  it('should delete last LinkExterno', async () => {
        const nbButtonsBeforeDelete = await linkExternoComponentsPage.countDeleteButtons();
        await linkExternoComponentsPage.clickOnLastDeleteButton();

        linkExternoDeleteDialog = new LinkExternoDeleteDialog();
        expect(await linkExternoDeleteDialog.getDialogTitle())
            .to.eq('informaApp.linkExterno.delete.question');
        await linkExternoDeleteDialog.clickOnConfirmButton();

        expect(await linkExternoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
