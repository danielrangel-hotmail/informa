import { Component, forwardRef } from '@angular/core';
import { ControlValueAccessor, NG_VALUE_ACCESSOR } from '@angular/forms';

@Component({
  selector: 'jhi-color-picker',
  templateUrl: './color-picker.component.html',
  styleUrls: ['./color-picker.component.scss'],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      // eslint-disable-next-line @typescript-eslint/no-use-before-define
      useExisting: forwardRef(() => ColorPickerComponent),
      multi: true
    }
  ]
})
export class ColorPickerComponent implements ControlValueAccessor {
  color = '';

  constructor() { }

  writeValue(value: string): void {
    if (value !== undefined) {
      this.color = value;
      this.propagateChange(this.color);
    }
  }

  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  propagateChange = (fn: any) => {};

  registerOnChange(fn: any): void {
    this.propagateChange = fn;
  }

  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  registerOnTouched(fn: any): void {
  }

  get value(): string {
    return this.color;
  }

  set value(val: string) {
    if (val) {
      this.color = val;
      this.propagateChange(this.color);
    }
  }

  colorPickerChanged(value: string): void {
    this.color = value;
    this.propagateChange(this.color);
  }

  setDisabledState(): void {

  }

}
