import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'grupo',
        loadChildren: () => import('./grupo/grupo.module').then(m => m.InformaGrupoModule)
      },
      {
        path: 'post',
        loadChildren: () => import('./post/post.module').then(m => m.InformaPostModule)
      },
      {
        path: 'mensagem',
        loadChildren: () => import('./mensagem/mensagem.module').then(m => m.InformaMensagemModule)
      },
      {
        path: 'arquivo',
        loadChildren: () => import('./arquivo/arquivo.module').then(m => m.InformaArquivoModule)
      },
      {
        path: 'link-externo',
        loadChildren: () => import('./link-externo/link-externo.module').then(m => m.InformaLinkExternoModule)
      },
      {
        path: 'push-subscription',
        loadChildren: () => import('./push-subscription/push-subscription.module').then(m => m.InformaPushSubscriptionModule)
      },
      {
        path: 'perfil-usuario',
        loadChildren: () => import('./perfil-usuario/perfil-usuario.module').then(m => m.InformaPerfilUsuarioModule)
      },
      {
        path: 'perfil-grupo',
        loadChildren: () => import('./perfil-grupo/perfil-grupo.module').then(m => m.InformaPerfilGrupoModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class InformaEntityModule {}
