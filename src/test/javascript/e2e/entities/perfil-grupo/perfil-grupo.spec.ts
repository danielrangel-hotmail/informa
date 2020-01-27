import { browser, ExpectedConditions as ec /* , protractor, promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  PerfilGrupoComponentsPage,
  /* PerfilGrupoDeleteDialog,
   */ PerfilGrupoUpdatePage
} from './perfil-grupo.page-object';

const expect = chai.expect;

describe('PerfilGrupo e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let perfilGrupoComponentsPage: PerfilGrupoComponentsPage;
  let perfilGrupoUpdatePage: PerfilGrupoUpdatePage;
  /* let perfilGrupoDeleteDialog: PerfilGrupoDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load PerfilGrupos', async () => {
    await navBarPage.goToEntity('perfil-grupo');
    perfilGrupoComponentsPage = new PerfilGrupoComponentsPage();
    await browser.wait(ec.visibilityOf(perfilGrupoComponentsPage.title), 5000);
    expect(await perfilGrupoComponentsPage.getTitle()).to.eq('informaApp.perfilGrupo.home.title');
  });

  it('should load create PerfilGrupo page', async () => {
    await perfilGrupoComponentsPage.clickOnCreateButton();
    perfilGrupoUpdatePage = new PerfilGrupoUpdatePage();
    expect(await perfilGrupoUpdatePage.getPageTitle()).to.eq('informaApp.perfilGrupo.home.createOrEditLabel');
    await perfilGrupoUpdatePage.cancel();
  });

  /*  it('should create and save PerfilGrupos', async () => {
        const nbButtonsBeforeCreate = await perfilGrupoComponentsPage.countDeleteButtons();

        await perfilGrupoComponentsPage.clickOnCreateButton();
        await promise.all([
            perfilGrupoUpdatePage.setCriacaoInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            perfilGrupoUpdatePage.setUltimaEdicaoInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            perfilGrupoUpdatePage.setVersaoInput('5'),
            perfilGrupoUpdatePage.perfilSelectLastOption(),
            perfilGrupoUpdatePage.grupoSelectLastOption(),
        ]);
        expect(await perfilGrupoUpdatePage.getCriacaoInput()).to.contain('2001-01-01T02:30', 'Expected criacao value to be equals to 2000-12-31');
        expect(await perfilGrupoUpdatePage.getUltimaEdicaoInput()).to.contain('2001-01-01T02:30', 'Expected ultimaEdicao value to be equals to 2000-12-31');
        expect(await perfilGrupoUpdatePage.getVersaoInput()).to.eq('5', 'Expected versao value to be equals to 5');
        const selectedFavorito = perfilGrupoUpdatePage.getFavoritoInput();
        if (await selectedFavorito.isSelected()) {
            await perfilGrupoUpdatePage.getFavoritoInput().click();
            expect(await perfilGrupoUpdatePage.getFavoritoInput().isSelected(), 'Expected favorito not to be selected').to.be.false;
        } else {
            await perfilGrupoUpdatePage.getFavoritoInput().click();
            expect(await perfilGrupoUpdatePage.getFavoritoInput().isSelected(), 'Expected favorito to be selected').to.be.true;
        }
        const selectedNotifica = perfilGrupoUpdatePage.getNotificaInput();
        if (await selectedNotifica.isSelected()) {
            await perfilGrupoUpdatePage.getNotificaInput().click();
            expect(await perfilGrupoUpdatePage.getNotificaInput().isSelected(), 'Expected notifica not to be selected').to.be.false;
        } else {
            await perfilGrupoUpdatePage.getNotificaInput().click();
            expect(await perfilGrupoUpdatePage.getNotificaInput().isSelected(), 'Expected notifica to be selected').to.be.true;
        }
        await perfilGrupoUpdatePage.save();
        expect(await perfilGrupoUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await perfilGrupoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /*  it('should delete last PerfilGrupo', async () => {
        const nbButtonsBeforeDelete = await perfilGrupoComponentsPage.countDeleteButtons();
        await perfilGrupoComponentsPage.clickOnLastDeleteButton();

        perfilGrupoDeleteDialog = new PerfilGrupoDeleteDialog();
        expect(await perfilGrupoDeleteDialog.getDialogTitle())
            .to.eq('informaApp.perfilGrupo.delete.question');
        await perfilGrupoDeleteDialog.clickOnConfirmButton();

        expect(await perfilGrupoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
