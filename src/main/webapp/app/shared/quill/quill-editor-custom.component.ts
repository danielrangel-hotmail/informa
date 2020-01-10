import { Component, forwardRef } from '@angular/core';
import { ControlValueAccessor, NG_VALUE_ACCESSOR } from '@angular/forms';
import 'quill-emoji/dist/quill-emoji.js'

@Component({
  selector: 'jhi-quill-editor',
  templateUrl: './quill-editor-custom.component.html',
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
  content = '';
  modules = {};
  constructor(
  ) {
    this.modules = {
      'emoji-shortname': true,
      'emoji-toolbar': true,
    }
  }

  // eslint-disable-next-line @typescript-eslint/tslint/config
  addBindingCreated(quill: any): void {
    quill.keyboard.addBinding({
      key: 'b'
    }, (range: any, context: any) => {
    })

    quill.keyboard.addBinding({
      key: 'B',
      shiftKey: true
    }, (range: any, context: any) => {
    })
  }

  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  onChanged: any = () => {}
  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  onTouched: any = () => {}

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

  contentChanged(event: any): void {
    this.onChanged(this.content);
  }
}
