import { Component, OnInit, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

interface CarouselSlide {
    image: string;
    title: string;
    subtitle: string;
    cta: string;
    link: string;
}

@Component({
    selector: 'app-hero-carousel',
    standalone: true,
    imports: [CommonModule, RouterLink],
    template: `
    <section class="relative h-[500px] md:h-[600px] lg:h-[700px] overflow-hidden">
      <!-- Slides -->
      @for (slide of slides; track slide.title; let i = $index) {
        <div 
          class="absolute inset-0 transition-opacity duration-1000"
          [class.opacity-100]="i === currentSlide"
          [class.opacity-0]="i !== currentSlide"
          [class.z-10]="i === currentSlide"
          [class.z-0]="i !== currentSlide">
          
          <!-- Background Image -->
          <div class="absolute inset-0">
            <img 
              [src]="slide.image" 
              [alt]="slide.title"
              class="w-full h-full object-cover"
            />
            <div class="absolute inset-0 bg-gradient-to-r from-foreground/80 via-foreground/50 to-transparent"></div>
          </div>

          <!-- Content -->
          <div class="relative h-full container-custom flex items-center">
            <div class="max-w-xl text-primary-foreground space-y-6 animate-slide-up">
              <span class="inline-block px-4 py-1 bg-accent/20 text-accent rounded-full text-sm font-medium">
                Handcrafted Excellence
              </span>
              <h1 class="font-display text-4xl md:text-5xl lg:text-6xl font-bold leading-tight">
                {{ slide.title }}
              </h1>
              <p class="text-lg md:text-xl text-primary-foreground/80 leading-relaxed">
                {{ slide.subtitle }}
              </p>
              <div class="flex gap-4">
                <a [routerLink]="slide.link" class="btn-primary">
                  {{ slide.cta }}
                </a>
                <a routerLink="/products" class="btn bg-primary-foreground/20 text-primary-foreground hover:bg-primary-foreground/30 px-6 py-3 backdrop-blur-sm">
                  View All
                </a>
              </div>
            </div>
          </div>
        </div>
      }

      <!-- Navigation Dots -->
      <div class="absolute bottom-6 left-1/2 -translate-x-1/2 flex gap-2 z-20">
        @for (slide of slides; track slide.title; let i = $index) {
          <button 
            (click)="goToSlide(i)"
            class="w-3 h-3 rounded-full transition-all duration-300"
            [class.bg-primary-foreground]="i === currentSlide"
            [class.w-8]="i === currentSlide"
            [class.bg-primary-foreground/40]="i !== currentSlide"
            [attr.aria-label]="'Go to slide ' + (i + 1)">
          </button>
        }
      </div>

      <!-- Arrow Controls -->
      <button 
        (click)="prevSlide()"
        class="absolute left-4 top-1/2 -translate-y-1/2 z-20 w-12 h-12 bg-primary-foreground/20 backdrop-blur-sm rounded-full flex items-center justify-center text-primary-foreground hover:bg-primary-foreground/30 transition-colors"
        aria-label="Previous slide">
        <svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 19l-7-7 7-7" />
        </svg>
      </button>
      <button 
        (click)="nextSlide()"
        class="absolute right-4 top-1/2 -translate-y-1/2 z-20 w-12 h-12 bg-primary-foreground/20 backdrop-blur-sm rounded-full flex items-center justify-center text-primary-foreground hover:bg-primary-foreground/30 transition-colors"
        aria-label="Next slide">
        <svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5l7 7-7 7" />
        </svg>
      </button>
    </section>
  `,
    styles: ``
})
export class HeroCarouselComponent implements OnInit, OnDestroy {
    slides: CarouselSlide[] = [
        {
            image: 'https://images.unsplash.com/photo-1565193566173-7a0ee3dbe261?w=1600&h=900&fit=crop',
            title: 'Discover Artisan Ceramics',
            subtitle: 'Handcrafted pottery and ceramic artworks from master artisans across Asia. Each piece tells a unique story.',
            cta: 'Shop Ceramics',
            link: '/products'
        },
        {
            image: 'https://images.unsplash.com/photo-1605627079912-97c3810a11a4?w=1600&h=900&fit=crop',
            title: 'Wooden Treasures',
            subtitle: 'Exquisite hand-carved wooden crafts showcasing centuries-old techniques and natural beauty.',
            cta: 'Shop Woodwork',
            link: '/products'
        },
        {
            image: 'https://images.unsplash.com/photo-1608571423902-eed4a5ad8108?w=1600&h=900&fit=crop',
            title: 'Aromatic Essentials',
            subtitle: 'Pure essential oils and handcrafted fragrances to transform your living spaces.',
            cta: 'Shop Scents',
            link: '/products'
        }
    ];

    currentSlide = 0;
    private autoplayInterval: ReturnType<typeof setInterval> | null = null;

    ngOnInit(): void {
        this.startAutoplay();
    }

    ngOnDestroy(): void {
        this.stopAutoplay();
    }

    startAutoplay(): void {
        this.autoplayInterval = setInterval(() => {
            this.nextSlide();
        }, 5000);
    }

    stopAutoplay(): void {
        if (this.autoplayInterval) {
            clearInterval(this.autoplayInterval);
        }
    }

    nextSlide(): void {
        this.currentSlide = (this.currentSlide + 1) % this.slides.length;
    }

    prevSlide(): void {
        this.currentSlide = (this.currentSlide - 1 + this.slides.length) % this.slides.length;
    }

    goToSlide(index: number): void {
        this.currentSlide = index;
        this.stopAutoplay();
        this.startAutoplay();
    }
}
