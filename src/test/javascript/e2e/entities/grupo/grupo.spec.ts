import { browser, ExpectedConditions as ec } from 'protractor';
import { NavBarGrupos, NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { GrupoCabecalhoPage, GrupoComponentsPage, GrupoDeleteDialog, GrupoUpdatePage } from './grupo.page-object';
import * as path from 'path';

const expect = chai.expect;


const expectNavBarGrupos = async (grupoCabecalhoPage: GrupoCabecalhoPage, corTesta1: string, corTesta2: string, nome: string, descricao: string, logoSource: string ) => {
  grupoCabecalhoPage = new GrupoCabecalhoPage();
  expect( await grupoCabecalhoPage.getCorTesta1()).to.containIgnoreSpaces(corTesta1);
  expect( await grupoCabecalhoPage.getCorTesta2()).to.containIgnoreSpaces(corTesta2);
  expect( await grupoCabecalhoPage.getNome()).to.eq(nome);
  expect( await grupoCabecalhoPage.getDescricao()).to.eq(descricao);
  expect( await grupoCabecalhoPage.getLogoSource()).to.containIgnoreSpaces(logoSource);
}

const expectGrupoUpdatePage = async (grupoUpdatePage: GrupoUpdatePage, title: string, nome: string, descricao: string, moderadores: string[], corTesta1: string, corTesta2: string, formal: boolean, opcional: boolean) => {
  expect( await grupoUpdatePage.getPageTitle(), 'titulo').to.eq(title);
  expect( await grupoUpdatePage.getNomeInput(), 'nome').to.eq(nome);
  expect( await grupoUpdatePage.getDescricaoInput(), 'descricao').to.eq(descricao);
  expect( await grupoUpdatePage.getModeradores(), 'moderadores').to.be.ofSize(moderadores.length);
  expect( await grupoUpdatePage.getModeradores(), 'moderadores').to.be.containingAllOf(moderadores);
  expect( await grupoUpdatePage.getCabecalhoSuperiorCorInput(), 'cor superior').to.containIgnoreSpaces(corTesta1);
  expect( await grupoUpdatePage.getCabecalhoInferiorCorInput(), 'cor inferior').to.containIgnoreSpaces(corTesta2);
  expect( await grupoUpdatePage.getFormalInput().isSelected(), 'Expected formal to be not selected').to.be.eq(formal);
  expect( await grupoUpdatePage.getOpcionalInput().isSelected(), 'Expected formal to be selected').to.be.eq(opcional);
}


describe('Grupo e2e test', () => {
  let navBarPage: NavBarPage;
  let navBarGrupos: NavBarGrupos;
  let signInPage: SignInPage;
  let resignInPage: SignInPage;
  let grupoComponentsPage: GrupoComponentsPage;
  let grupoUpdatePage: GrupoUpdatePage;
  let grupoCabecalhoPage: GrupoCabecalhoPage;
  let grupoDeleteDialog: GrupoDeleteDialog;
  const fileNameToUpload = 'logo-jhipster.png';
  const fileToUpload = '../../../../../../src/main/webapp/content/images/' + fileNameToUpload;
  const absolutePath = path.resolve(__dirname, fileToUpload);

  beforeEach(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
  });

  it('should carregar a página de criação do grupo, com valores default', async () => {
    await signInPage.autoSignInEnvironment('admin', 'admin', 'Zerado');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    navBarGrupos = new NavBarGrupos();
    await navBarGrupos.clickOnCrieNovoGrupo();
    grupoCabecalhoPage = new GrupoCabecalhoPage();
    await expectNavBarGrupos(
      grupoCabecalhoPage,
      "rgb(255, 165, 0)",
      "rgb(255, 255, 255)",
      "",
      "",
      "logo-grupo-padrao.png");
    grupoUpdatePage = new GrupoUpdatePage();
    await expectGrupoUpdatePage(
      grupoUpdatePage,
      "Crie um novo grupo",
      "",
      "",
      ['Administrator Administrator'],
      "rgb(255, 165, 0)",
      "rgb(255, 255, 255)",
      false,
      true
    )
    await grupoUpdatePage.cancel();
  });

  it('should create and save Grupos', async () => {
    await signInPage.autoSignInEnvironment('admin', 'admin', 'Zerado');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    navBarGrupos = new NavBarGrupos();
    await navBarGrupos.clickOnCrieNovoGrupo();
    grupoUpdatePage = new GrupoUpdatePage();
    grupoCabecalhoPage = new GrupoCabecalhoPage();
    // eslint-disable-next-line no-console
    await grupoUpdatePage.setNomeInput('nome');
    await grupoUpdatePage.setDescricaoInput('descricao');
    await grupoUpdatePage.addModerador('User');
    await grupoUpdatePage.addTopico('Esporte');
    await grupoUpdatePage.setCabecalhoSuperiorCorInput('#aafafa');
    await grupoUpdatePage.nomeInput.click();
    await grupoUpdatePage.setCabecalhoInferiorCorInput('#0afafa');
    await grupoUpdatePage.nomeInput.click();
    await grupoUpdatePage.save();
    await navBarGrupos.grupos.first().click();
    await browser.sleep(5000);
    await expectNavBarGrupos(
        grupoCabecalhoPage,
        "rgb(170, 250, 250)",
        "rgb(10, 250, 250)",
        "nome",
        "descricao",
        "logo-grupo-padrao.png");

    //Reloga para verificar que ficou tudo salvo mesmo
    await navBarPage.autoSignOut();
    resignInPage = await navBarPage.getSignInPage();
    await resignInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    await navBarGrupos.editGrupo("nome");
    await expectGrupoUpdatePage(
      grupoUpdatePage,
      "Edite o seu grupo",
      "nome",
      "descricao",
      ['Administrator Administrator', 'User User'],
      "rgb(170, 250, 250)",
      "rgb(10, 250, 250)",
      false,
      true
    )
  });

  // it('should delete last Grupo', async () => {
  //   const nbButtonsBeforeDelete = await grupoComponentsPage.countDeleteButtons();
  //   await grupoComponentsPage.clickOnLastDeleteButton();
  //
  //   grupoDeleteDialog = new GrupoDeleteDialog();
  //   expect(await grupoDeleteDialog.getDialogTitle()).to.eq('informaApp.grupo.delete.question');
  //   await grupoDeleteDialog.clickOnConfirmButton();
  //
  //   expect(await grupoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  // });

  afterEach(async () => {
    await navBarPage.autoSignOutResetEnvironment();
  });
});
