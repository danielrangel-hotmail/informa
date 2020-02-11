import { browser, ExpectedConditions as ec /* , protractor, promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  PostReacaoComponentsPage,
  /* PostReacaoDeleteDialog,
   */ PostReacaoUpdatePage
} from './post-reacao.page-object';

const expect = chai.expect;

describe('PostReacao e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let postReacaoComponentsPage: PostReacaoComponentsPage;
  let postReacaoUpdatePage: PostReacaoUpdatePage;
  /* let postReacaoDeleteDialog: PostReacaoDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load PostReacaos', async () => {
    await navBarPage.goToEntity('post-reacao');
    postReacaoComponentsPage = new PostReacaoComponentsPage();
    await browser.wait(ec.visibilityOf(postReacaoComponentsPage.title), 5000);
    expect(await postReacaoComponentsPage.getTitle()).to.eq('informaApp.postReacao.home.title');
  });

  it('should load create PostReacao page', async () => {
    await postReacaoComponentsPage.clickOnCreateButton();
    postReacaoUpdatePage = new PostReacaoUpdatePage();
    expect(await postReacaoUpdatePage.getPageTitle()).to.eq('informaApp.postReacao.home.createOrEditLabel');
    await postReacaoUpdatePage.cancel();
  });

  /*  it('should create and save PostReacaos', async () => {
        const nbButtonsBeforeCreate = await postReacaoComponentsPage.countDeleteButtons();

        await postReacaoComponentsPage.clickOnCreateButton();
        await promise.all([
            postReacaoUpdatePage.setCriacaoInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            postReacaoUpdatePage.setUltimaEdicaoInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            postReacaoUpdatePage.setVersaoInput('5'),
            postReacaoUpdatePage.setReacaoInput('reacao'),
            postReacaoUpdatePage.perfilSelectLastOption(),
            postReacaoUpdatePage.postSelectLastOption(),
        ]);
        expect(await postReacaoUpdatePage.getCriacaoInput()).to.contain('2001-01-01T02:30', 'Expected criacao value to be equals to 2000-12-31');
        expect(await postReacaoUpdatePage.getUltimaEdicaoInput()).to.contain('2001-01-01T02:30', 'Expected ultimaEdicao value to be equals to 2000-12-31');
        expect(await postReacaoUpdatePage.getVersaoInput()).to.eq('5', 'Expected versao value to be equals to 5');
        expect(await postReacaoUpdatePage.getReacaoInput()).to.eq('reacao', 'Expected Reacao value to be equals to reacao');
        await postReacaoUpdatePage.save();
        expect(await postReacaoUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await postReacaoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /*  it('should delete last PostReacao', async () => {
        const nbButtonsBeforeDelete = await postReacaoComponentsPage.countDeleteButtons();
        await postReacaoComponentsPage.clickOnLastDeleteButton();

        postReacaoDeleteDialog = new PostReacaoDeleteDialog();
        expect(await postReacaoDeleteDialog.getDialogTitle())
            .to.eq('informaApp.postReacao.delete.question');
        await postReacaoDeleteDialog.clickOnConfirmButton();

        expect(await postReacaoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
