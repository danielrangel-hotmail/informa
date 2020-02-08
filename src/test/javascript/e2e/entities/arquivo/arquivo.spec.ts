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

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
