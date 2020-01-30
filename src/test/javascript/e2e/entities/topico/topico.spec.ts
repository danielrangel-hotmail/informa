import { browser, ExpectedConditions as ec /* , promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  TopicoComponentsPage,
  /* TopicoDeleteDialog,
   */ TopicoUpdatePage
} from './topico.page-object';

const expect = chai.expect;

describe('Topico e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let topicoComponentsPage: TopicoComponentsPage;
  let topicoUpdatePage: TopicoUpdatePage;
  /* let topicoDeleteDialog: TopicoDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Topicos', async () => {
    await navBarPage.goToEntity('topico');
    topicoComponentsPage = new TopicoComponentsPage();
    await browser.wait(ec.visibilityOf(topicoComponentsPage.title), 5000);
    expect(await topicoComponentsPage.getTitle()).to.eq('informaApp.topico.home.title');
  });

  it('should load create Topico page', async () => {
    await topicoComponentsPage.clickOnCreateButton();
    topicoUpdatePage = new TopicoUpdatePage();
    expect(await topicoUpdatePage.getPageTitle()).to.eq('informaApp.topico.home.createOrEditLabel');
    await topicoUpdatePage.cancel();
  });

  /*  it('should create and save Topicos', async () => {
        const nbButtonsBeforeCreate = await topicoComponentsPage.countDeleteButtons();

        await topicoComponentsPage.clickOnCreateButton();
        await promise.all([
            topicoUpdatePage.setVersaoInput('5'),
            topicoUpdatePage.setNomeInput('nome'),
            topicoUpdatePage.autorSelectLastOption(),
            topicoUpdatePage.substitutoSelectLastOption(),
        ]);
        expect(await topicoUpdatePage.getVersaoInput()).to.eq('5', 'Expected versao value to be equals to 5');
        expect(await topicoUpdatePage.getNomeInput()).to.eq('nome', 'Expected Nome value to be equals to nome');
        await topicoUpdatePage.save();
        expect(await topicoUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await topicoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /*  it('should delete last Topico', async () => {
        const nbButtonsBeforeDelete = await topicoComponentsPage.countDeleteButtons();
        await topicoComponentsPage.clickOnLastDeleteButton();

        topicoDeleteDialog = new TopicoDeleteDialog();
        expect(await topicoDeleteDialog.getDialogTitle())
            .to.eq('informaApp.topico.delete.question');
        await topicoDeleteDialog.clickOnConfirmButton();

        expect(await topicoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
