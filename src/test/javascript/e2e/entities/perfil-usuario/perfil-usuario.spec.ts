import { browser, ExpectedConditions as ec /* , protractor, promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  PerfilUsuarioComponentsPage,
  /* PerfilUsuarioDeleteDialog,
   */ PerfilUsuarioUpdatePage
} from './perfil-usuario.page-object';

const expect = chai.expect;

describe('PerfilUsuario e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let perfilUsuarioComponentsPage: PerfilUsuarioComponentsPage;
  let perfilUsuarioUpdatePage: PerfilUsuarioUpdatePage;
  /* let perfilUsuarioDeleteDialog: PerfilUsuarioDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load PerfilUsuarios', async () => {
    await navBarPage.goToEntity('perfil-usuario');
    perfilUsuarioComponentsPage = new PerfilUsuarioComponentsPage();
    await browser.wait(ec.visibilityOf(perfilUsuarioComponentsPage.title), 5000);
    expect(await perfilUsuarioComponentsPage.getTitle()).to.eq('informaApp.perfilUsuario.home.title');
  });

  it('should load create PerfilUsuario page', async () => {
    await perfilUsuarioComponentsPage.clickOnCreateButton();
    perfilUsuarioUpdatePage = new PerfilUsuarioUpdatePage();
    expect(await perfilUsuarioUpdatePage.getPageTitle()).to.eq('informaApp.perfilUsuario.home.createOrEditLabel');
    await perfilUsuarioUpdatePage.cancel();
  });

  /*  it('should create and save PerfilUsuarios', async () => {
        const nbButtonsBeforeCreate = await perfilUsuarioComponentsPage.countDeleteButtons();

        await perfilUsuarioComponentsPage.clickOnCreateButton();
        await promise.all([
            perfilUsuarioUpdatePage.setCriacaoInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            perfilUsuarioUpdatePage.setUltimaEdicaoInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            perfilUsuarioUpdatePage.setVersaoInput('5'),
            perfilUsuarioUpdatePage.setEntradaNaEmpresaInput('2000-12-31'),
            perfilUsuarioUpdatePage.setSaidaDaEmpresaInput('2000-12-31'),
            perfilUsuarioUpdatePage.setNascimentoInput('2000-12-31'),
            perfilUsuarioUpdatePage.setSkypeInput('skype'),
            perfilUsuarioUpdatePage.usuarioSelectLastOption(),
        ]);
        expect(await perfilUsuarioUpdatePage.getCriacaoInput()).to.contain('2001-01-01T02:30', 'Expected criacao value to be equals to 2000-12-31');
        expect(await perfilUsuarioUpdatePage.getUltimaEdicaoInput()).to.contain('2001-01-01T02:30', 'Expected ultimaEdicao value to be equals to 2000-12-31');
        expect(await perfilUsuarioUpdatePage.getVersaoInput()).to.eq('5', 'Expected versao value to be equals to 5');
        expect(await perfilUsuarioUpdatePage.getEntradaNaEmpresaInput()).to.eq('2000-12-31', 'Expected entradaNaEmpresa value to be equals to 2000-12-31');
        expect(await perfilUsuarioUpdatePage.getSaidaDaEmpresaInput()).to.eq('2000-12-31', 'Expected saidaDaEmpresa value to be equals to 2000-12-31');
        expect(await perfilUsuarioUpdatePage.getNascimentoInput()).to.eq('2000-12-31', 'Expected nascimento value to be equals to 2000-12-31');
        expect(await perfilUsuarioUpdatePage.getSkypeInput()).to.eq('skype', 'Expected Skype value to be equals to skype');
        await perfilUsuarioUpdatePage.save();
        expect(await perfilUsuarioUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await perfilUsuarioComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /*  it('should delete last PerfilUsuario', async () => {
        const nbButtonsBeforeDelete = await perfilUsuarioComponentsPage.countDeleteButtons();
        await perfilUsuarioComponentsPage.clickOnLastDeleteButton();

        perfilUsuarioDeleteDialog = new PerfilUsuarioDeleteDialog();
        expect(await perfilUsuarioDeleteDialog.getDialogTitle())
            .to.eq('informaApp.perfilUsuario.delete.question');
        await perfilUsuarioDeleteDialog.clickOnConfirmButton();

        expect(await perfilUsuarioComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
