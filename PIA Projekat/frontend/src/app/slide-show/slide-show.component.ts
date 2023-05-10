import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-slide-show',
  templateUrl: './slide-show.component.html',
  styleUrls: ['./slide-show.component.css']
})
export class SlideShowComponent {

  @Input() images;
  currentImageIndex = 0;

  prevImage() {
    this.currentImageIndex--;
    if (this.currentImageIndex < 0) {
      this.currentImageIndex = this.images.length - 1;
    }
  }

  nextImage() {
    this.currentImageIndex++;
    if (this.currentImageIndex === this.images.length) {
      this.currentImageIndex = 0;
    }
  }
}
