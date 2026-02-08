import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { ProductCardComponent } from '../../../../shared/components/product-card/product-card.component';
import { getFeaturedProducts } from '../../../../shared/data/products.data';

@Component({
    selector: 'app-featured-products',
    standalone: true,
    imports: [CommonModule, RouterLink, ProductCardComponent],
    template: `
    <section class="py-16">
      <div class="container-custom">
        <!-- Section Header -->
        <div class="flex flex-col md:flex-row md:items-end justify-between gap-4 mb-10">
          <div>
            <span class="text-accent font-medium text-sm uppercase tracking-wider">Handpicked</span>
            <h2 class="font-display text-3xl md:text-4xl font-bold text-foreground mt-2">
              Featured Products
            </h2>
            <p class="text-muted-foreground mt-2 max-w-xl">
              Our most loved artisan creations, selected for their exceptional craftsmanship
            </p>
          </div>
          <a 
            routerLink="/products" 
            class="btn-outline self-start md:self-auto">
            View All Products
            <svg xmlns="http://www.w3.org/2000/svg" class="h-4 w-4 ml-2" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5l7 7-7 7" />
            </svg>
          </a>
        </div>

        <!-- Products Grid -->
        <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6">
          @for (product of featuredProducts; track product.id) {
            <app-product-card [product]="product" />
          }
        </div>
      </div>
    </section>
  `,
    styles: ``
})
export class FeaturedProductsComponent {
    featuredProducts = getFeaturedProducts();
}
