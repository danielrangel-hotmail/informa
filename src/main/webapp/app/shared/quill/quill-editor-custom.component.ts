import { Component, ElementRef, EventEmitter, forwardRef, Input, Output, ViewChild } from '@angular/core';
import { ControlValueAccessor, NG_VALUE_ACCESSOR } from '@angular/forms';
import { QuillEditorComponent } from 'ngx-quill'
import { UserService } from '../../core/user/user.service';
import 'quill-emoji/dist/quill-emoji.js';
import 'quill-mention';
import Quill from 'quill';
import {User} from '../../core/user/user.model';
import { HttpResponse } from '@angular/common/http';

@Component({
  selector: 'jhi-quill-editor',
  templateUrl: './quill-editor-custom.component.html',
  styleUrls: [ './quil-editor-custom.component.scss'],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      // eslint-disable-next-line @typescript-eslint/no-use-before-define
      useExisting: forwardRef(() => QuillEditorCustomComponent),
      multi: true
    }
  ]
})
export class QuillEditorCustomComponent implements ControlValueAccessor {
  @ViewChild(QuillEditorComponent, { static: true }) editor?: QuillEditorComponent;
  // @ViewChild('quillEditorComponent', { static: true }) emojiPalette?: ElementRef;
  @Input() likeWhatsapp = true;
  @Output() entered = new EventEmitter();
  @Output() setaParaCima = new EventEmitter();
  content = '';
  modules = {};
  users: User[] = [];
  constructor(
    private userService: UserService,
    private elementRef: ElementRef
  ) {
    this.userService.query(
      {
        page: 0,
        size: 1000,
        sort: ['firstName', 'lastName', 'id']
      }
    ).subscribe((res: HttpResponse<User[]>) => this.onSuccess(res.body));;
    this.modules = {
      'emoji-shortname': true,
      'emoji-toolbar': { top: '-250px' },
      'mention': {
        allowedChars: /^[A-Za-z\sÅÄÖåäö]*$/,
        onSelect: (item: any, insertItem: (arg0: any) => void) => {
          if (this.editor != null) {
            const editor = this.editor.quillEditor as Quill;
            insertItem(item);
            // necessary because quill-mention triggers changes as 'api' instead of 'user'
            editor.insertText(editor.getLength() - 1, '', 'user')
          }
        },
        source: (searchTerm: any, renderList: any): void => {
          const values = this.users.map((user: User) => ( {id: user.id, value: (user.firstName + " " + user.lastName)}));
          if (searchTerm.length === 0) {
            renderList(values, searchTerm)
          } else {
            const matches: any[] = [];
            values.forEach((entry: any) => {
              if (entry.value.toLowerCase().indexOf(searchTerm.toLowerCase()) !== -1) {
                matches.push(entry);
              }
            })
            renderList(matches, searchTerm)
          }
        }
      }
    }
  }

  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  onChanged: any = () => {};
  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  onTouched: any = () => {
  };

  registerOnChange(fn: any): void {
    this.onChanged = fn;
  }

  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  registerOnTouched(fn: any): void {
    this.onTouched = fn
  }

  writeValue(obj: any): void {
    this.content = obj;
  }

  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  contentChanged(event: any): void {
    this.onChanged(this.content);
  }

  private onSuccess(users: User[] | null): void {
    if (users != null) this.users = users;
  }

  aposCriacao(quill: any): void {
    if (!this.likeWhatsapp) return;
    quill.keyboard.addBinding(38, () => {
      if ((this.content === null) || (this.content === '{"ops":[{"insert":"\\n"}]}')) {
        this.setaParaCima.emit("clicou seta para cima");
      };
    });

    // Tentativa de fazer com que o return se comporte como submit
    // O problema é fazer com que no comportamento do emoji ou da menção ele não dê submit
    // Talvez dê para filtrar com o context
    // const binds: any[] = quill.keyboard.bindings[13];
    // binds.forEach(bind => {
    //   bind.shiftKey = true;
    // });
    // quill.keyboard.addBinding({ key: 13 }, (range: any, context: any) => {
    //   this.entered.emit("entered");
    // });
  }
}
