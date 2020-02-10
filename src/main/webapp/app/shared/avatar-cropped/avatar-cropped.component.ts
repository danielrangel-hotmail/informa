import { Component, EventEmitter, Output } from '@angular/core';
import { ImageCroppedEvent, ImageTransform } from 'ngx-image-cropper';

export interface IImageCroped {
  contentType?: string;
  content?: string;
}

@Component({
  selector: 'jhi-avatar-cropped',
  templateUrl: './avatar-cropped.component.html',
  styleUrls: ['./avatar-cropped.component.scss'],
})
export class AvatarCroppedComponent  {
  @Output() croppedImageChanged: EventEmitter<IImageCroped> = new EventEmitter();
  imageChangedEvent: any = '';
  croppedImage: any = '';
  canvasRotation = 0;
  rotation = 0;
  scale = 1;
  showCropper = false;
  containWithinAspectRatio = false;
  transform: ImageTransform = {};

  constructor() {
  }


  fileChangeEvent(event: any): void {
    this.imageChangedEvent = event;
  }

  imageCropped(event: ImageCroppedEvent): void {
    this.croppedImage = event.base64;
    const cropped = this.croppedImage.split(";base64,");
    if (cropped.length === 2) {
      const contentFinal = {
        contentType: cropped[0].split("data:")[1],
          content: cropped[1]
      };
      // eslint-disable-next-line no-console
      console.log('content');
      // eslint-disable-next-line no-console
      console.log(contentFinal);
      this.croppedImageChanged.emit(contentFinal);
    }
  }

  imageLoaded(): void {
    this.showCropper = true;
  }

  cropperReady(event: any): void {
    // console.log('Cropper ready', sourceImageDimensions);
  }

  loadImageFailed(): void {
    // console.log('Load failed');
  }

  rotateLeft(): void {
    this.canvasRotation--;
    this.flipAfterRotate();
  }

  rotateRight(): void {
    this.canvasRotation++;
    this.flipAfterRotate();
  }

  private flipAfterRotate(): void {
    const flippedH = this.transform.flipH;
    const flippedV = this.transform.flipV;
    this.transform = {
      ...this.transform,
      flipH: flippedV,
      flipV: flippedH
    };
  }


  flipHorizontal(): void {
    this.transform = {
      ...this.transform,
      flipH: !this.transform.flipH
    };
  }

  flipVertical(): void {
    this.transform = {
      ...this.transform,
      flipV: !this.transform.flipV
    };
  }

  resetImage(): void {
    this.scale = 1;
    this.rotation = 0;
    this.canvasRotation = 0;
    this.transform = {};
  }

  zoomOut(): void {
    this.scale -= .1;
    this.transform = {
      ...this.transform,
      scale: this.scale
    };
  }

  zoomIn(): void {
    this.scale += .1;
    this.transform = {
      ...this.transform,
      scale: this.scale
    };
  }
}
