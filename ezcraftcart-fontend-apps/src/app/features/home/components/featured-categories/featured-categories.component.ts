import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { categories } from '../../../../shared/data/products.data';

@Component({
    selector: 'app-featured-categories',
    standalone: true,
    imports: [CommonModule, RouterLink],
    template: `
    <section id="categories" class="py-16 artisan-gradient">
      <div class="container-custom">
        <!-- Section Header -->
        <div class="text-center mb-12">
          <span class="text-accent font-medium text-sm uppercase tracking-wider">Browse By</span>
          <h2 class="font-display text-3xl md:text-4xl font-bold text-foreground mt-2">
            Artisan Categories
          </h2>
          <p class="text-muted-foreground mt-4 max-w-2xl mx-auto">
            Explore our curated collection of handcrafted goods from skilled artisans around the world
          </p>
        </div>

        <!-- Categories Grid -->
        <div class="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-6 gap-4 md:gap-6">
          @for (category of categories; track category.id) {
            <a 
              [routerLink]="['/products']"
              [queryParams]="{category: category.slug}"
              class="group relative aspect-square rounded-xl overflow-hidden cursor-pointer">
              
              <!-- Background Image -->
              <img 
                [src]="category.image" 
                [alt]="category.name"
                loading="lazy"
                class="absolute inset-0 w-full h-full object-cover transition-transform duration-500 group-hover:scale-110"
              />
              
              <!-- Overlay -->
              <div class="absolute inset-0 bg-gradient-to-t from-foreground/80 via-foreground/40 to-transparent 
                          transition-opacity duration-300 group-hover:from-foreground/90"></div>
              
              <!-- Content -->
              <div class="absolute inset-0 flex flex-col justify-end p-4">
                <h3 class="font-display text-lg font-semibold text-primary-foreground leading-tight">
                  {{ category.name }}
                </h3>
                <p class="text-sm text-primary-foreground/70 mt-1">
                  {{ category.productCount }} products
                </p>
              </div>

              <!-- Hover Arrow -->
              <div class="absolute top-4 right-4 w-8 h-8 bg-primary-foreground/20 backdrop-blur-sm rounded-full 
                          flex items-center justify-center opacity-0 -translate-x-2 
                          group-hover:opacity-100 group-hover:translate-x-0 transition-all duration-300">
                <svg xmlns="http://www.w3.org/2000/svg" class="h-4 w-4 text-primary-foreground" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5l7 7-7 7" />
                </svg>
              </div>
            </a>
          }
        </div>
      </div>
    </section>
  `,
    styles: ``
})
export class FeaturedCategoriesComponent {
    categories = categories;
}
