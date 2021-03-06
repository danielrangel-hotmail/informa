import { AfterViewInit, Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from '../../shared/constants/input.constants';
import { Post } from '../../shared/model/post.model';
import { IPost } from '../../shared/model/post.interface';
import { PostService } from '../shared-post/post.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { GrupoService } from 'app/entities/grupo/grupo.service';
import { PerfilGrupoViewService } from 'app/layouts/navbar/perfil-grupo-view.service';
import { IGrupo } from '../../shared/model/grupo.interface';

type SelectableEntity = IUser | IGrupo;

@Component({
  selector: 'jhi-post-update',
  templateUrl: './post-update.component.html'
})
export class PostUpdateComponent implements OnInit, AfterViewInit {
  isSaving = false;
  isNavbarCollapsed = false;
  users: IUser[] = [];

  grupos: IGrupo[] = [];

  editForm = this.fb.group({
    id: [],
    versao: [],
    conteudo: [],
    oficial: [],
    grupoId: [null],
  });

  constructor(
    protected postService: PostService,
    protected userService: UserService,
    protected grupoService: GrupoService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    private fb: FormBuilder,
    protected perfilGrupoViewService: PerfilGrupoViewService
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ post }) => {
      this.updateForm(post);

      this.userService
        .query()
        .pipe(
          map((res: HttpResponse<IUser[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IUser[]) => (this.users = resBody));

      this.grupoService
        .query()
        .pipe(
          map((res: HttpResponse<IGrupo[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IGrupo[]) => (this.grupos = resBody));
    });
  }

  updateForm(post: IPost): void {
    this.editForm.patchValue({
      id: post.id,
      versao: post.versao,
      conteudo: post.conteudo,
      oficial: post.oficial,
      grupoId: post.grupoId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const post = this.createFromForm();
    // eslint-disable-next-line no-console
    console.log(post);
    if (post.id !== undefined) {
      this.subscribeToSaveResponse(this.postService.update(post));
    } else {
      this.subscribeToSaveResponse(this.postService.create(post));
    }
  }

  private createFromForm(): IPost {
    return {
      ...new Post(),
      id: this.editForm.get(['id'])!.value,
      versao: this.editForm.get(['versao'])!.value,
      conteudo: this.editForm.get(['conteudo'])!.value,
      oficial: this.editForm.get(['oficial'])!.value,
      grupoId: this.editForm.get(['grupoId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPost>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }

  collapseNavbar(): void {
    this.isNavbarCollapsed = true;
  }

  toggleNavbar(): void {
    this.isNavbarCollapsed = !this.isNavbarCollapsed;
  }

  ngAfterViewInit(): void {
    this.perfilGrupoViewService.navega("editando post");
  }

}
